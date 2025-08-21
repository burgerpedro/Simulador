package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.exception.DadosInvalidosException;
import br.gov.caixa.Simulador.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.Simulador.model.EntradaSimulacao;
import br.gov.caixa.Simulador.model.dto.SimulacaoRequestDTO;
import br.gov.caixa.Simulador.model.dto.SimulacaoResponseDTO;
import br.gov.caixa.Simulador.model.external.Produto;
import br.gov.caixa.Simulador.model.ResultadoSimulacao;
import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.model.request.SimulacaoDetalhe;
import br.gov.caixa.Simulador.repository.external.ProdutoRepository;
import br.gov.caixa.Simulador.repository.local.SimulacaoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SimulacaoService {

    @Autowired
    private SimuladorEmprestimo simuladorEmprestimo;

    @Autowired
    private SimulacaoRepository simulacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EventHubService eventHubService;

    // Objeto para serialização JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public SimulacaoResponseDTO simularEGravar(SimulacaoRequestDTO requestDTO) {

        if (requestDTO.getValorDesejado() == null || requestDTO.getValorDesejado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("O valor desejado deve ser maior que zero.");
        }
        if (requestDTO.getPrazo() <= 0) {
            throw new DadosInvalidosException("O prazo deve ser um número positivo.");
        }

        Optional<Produto> produtoOptional = produtoRepository.findByRange(requestDTO.getValorDesejado(), requestDTO.getPrazo());

        if (produtoOptional.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Não foi encontrado produto que se encaixe nas condições de valor e prazo.");
        }

        Produto produto = produtoOptional.get();

        ResultadoSimulacao resultado = simuladorEmprestimo.simularEmprestimo(
                requestDTO.getValorDesejado(),
                requestDTO.getPrazo(),
                produto.getCoProduto(),
                produto.getNoProduto(),
                produto.getPcTaxaJuros()
        );

        // Calcular o valor total das parcelas para persistir no banco local
        BigDecimal valorTotalParcelas = resultado.getResultadoAmortizacao().stream()
                .flatMap(res -> res.getParcelas().stream())
                .map(p -> p.getValorPrestacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Criar e preencher a entidade de Simulação para salvar no banco local
        Simulacao simulacao = new Simulacao();
        simulacao.setCodigoProduto(resultado.getCodigoProduto());
        simulacao.setDescricaoProduto(resultado.getDescricaoProduto());
        simulacao.setTaxaJuros(resultado.getTaxaJuros());
        simulacao.setPrazo(requestDTO.getPrazo());
        simulacao.setValorDesejado(requestDTO.getValorDesejado());
        simulacao.setValorTotalParcelas(valorTotalParcelas);
        simulacao.setDataReferencia(LocalDate.now());

        // Serializa o resultado da simulacao para JSON antes de salvar
        try {
            String resultadoSimulacaoJson = objectMapper.writeValueAsString(resultado.getResultadoAmortizacao());
            simulacao.setResultadoSimulacaoJson(resultadoSimulacaoJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar o resultado da simulação para JSON.", e);
        }

        simulacaoRepository.save(simulacao);
        resultado.setIdSimulacao(simulacao.getIdSimulacao());

        // Enviar o envelope JSON para o EventHub
        try {
            String jsonPayload = objectMapper.writeValueAsString(resultado);
            eventHubService.enviarEvento(jsonPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar o resultado para o EventHub.", e);
        }

        // Converte o resultado para DTO antes de retornar
        return new SimulacaoResponseDTO(resultado);
    }

    public Page<Simulacao> listarSimulacoes(Pageable pageable) {
        return simulacaoRepository.findAll(pageable);
    }

    public List<SimulacaoDetalhe> buscarSimulacoesPorDia(LocalDate dataReferencia) {
        return simulacaoRepository.findSimulacoesByDay(dataReferencia);
    }
}
