package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.dto.SimulacaoRequestDTO;
import br.gov.caixa.Simulador.dto.SimulacaoResponseDTO;
import br.gov.caixa.Simulador.exception.DadosInvalidosException;
import br.gov.caixa.Simulador.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.Simulador.model.Parcela;
import br.gov.caixa.Simulador.model.ResultadoSimulacao;
import br.gov.caixa.Simulador.model.external.Produto;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public SimulacaoResponseDTO simularEGravar(SimulacaoRequestDTO requestDTO) {
        validarRequest(requestDTO);

        Produto produto = buscarProduto(requestDTO);

        ResultadoSimulacao resultado = simularEmprestimo(requestDTO, produto);

        Simulacao simulacao = criarSimulacao(requestDTO, resultado);

        salvarSimulacao(simulacao, resultado);

        enviarParaEventHub(resultado);

        return new SimulacaoResponseDTO(resultado);
    }

       private void validarRequest(SimulacaoRequestDTO requestDTO) {
        if (requestDTO.getValorDesejado() == null || requestDTO.getValorDesejado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("O valor desejado deve ser maior que zero.");
        }
        if (requestDTO.getPrazo() <= 0) {
            throw new DadosInvalidosException("O prazo deve ser maior que 0.");
        }
    }

    private Produto buscarProduto(SimulacaoRequestDTO requestDTO) {
        return produtoRepository.findByRange(requestDTO.getValorDesejado(), requestDTO.getPrazo())
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Não foi encontrado produto que se encaixe nas condições de valor e prazo."));
    }

    private ResultadoSimulacao simularEmprestimo(SimulacaoRequestDTO requestDTO, Produto produto) {
        return simuladorEmprestimo.simularEmprestimo(
                requestDTO.getValorDesejado(),
                requestDTO.getPrazo(),
                produto.getCoProduto(),
                produto.getNoProduto(),
                produto.getPcTaxaJuros()
        );
    }

    private Simulacao criarSimulacao(SimulacaoRequestDTO requestDTO, ResultadoSimulacao resultado) {

        BigDecimal valorTotalSac = resultado.getResultadoSimulacao().stream()
                .filter(res -> "SAC".equals(res.getTipo()))
                .flatMap(res -> res.getParcelas().stream())
                .map(Parcela::getValorPrestacao)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorTotalPrice = resultado.getResultadoSimulacao().stream()
                .filter(res -> "PRICE".equals(res.getTipo()))
                .flatMap(res -> res.getParcelas().stream())
                .map(Parcela::getValorPrestacao)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Simulacao simulacao = new Simulacao();
        simulacao.setCodigoProduto(resultado.getCodigoProduto());
        simulacao.setDescricaoProduto(resultado.getDescricaoProduto());
        simulacao.setTaxaJuros(resultado.getTaxaJuros());
        simulacao.setPrazo(requestDTO.getPrazo());
        simulacao.setValorDesejado(requestDTO.getValorDesejado());
        simulacao.setValorTotalSac(valorTotalSac);
        simulacao.setValorTotalPrice(valorTotalPrice);
        simulacao.setDataReferencia(LocalDate.now());

        try {
            String resultadoSimulacaoJson = objectMapper.writeValueAsString(resultado.getResultadoSimulacao());
            simulacao.setResultadoSimulacaoJson(resultadoSimulacaoJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar o resultado da simulação para JSON.", e);
        }

        return simulacao;
    }


    private void salvarSimulacao(Simulacao simulacao, ResultadoSimulacao resultado) {
        simulacaoRepository.save(simulacao);
        resultado.setIdSimulacao(simulacao.getIdSimulacao());
    }

    private void enviarParaEventHub(ResultadoSimulacao resultado) {
        try {
            String jsonPayload = objectMapper.writeValueAsString(resultado);
            eventHubService.enviarEvento(jsonPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar o resultado para o EventHub.", e);
        }
    }

    public Page<Simulacao> listarSimulacoes(Pageable pageable) {
        return simulacaoRepository.findAll(pageable);
    }

    public List<SimulacaoDetalhe> buscarSimulacoesPorDia(LocalDate dataReferencia) {
        return simulacaoRepository.findSimulacoesByDay(dataReferencia);
    }

    public List<SimulacaoDetalhe> buscarSimulacoesPorDiaEProduto(LocalDate data, int codigoProduto) {
        return simulacaoRepository.findSimulacoesByDataAndProduto(data, codigoProduto);
    }
}