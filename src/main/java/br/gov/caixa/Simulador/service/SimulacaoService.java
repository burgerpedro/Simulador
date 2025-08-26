package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.dto.SimulacaoRequestDTO;
import br.gov.caixa.Simulador.dto.SimulacaoResponseDTO;
import br.gov.caixa.Simulador.exception.DadosInvalidosException;
import br.gov.caixa.Simulador.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.Simulador.exception.SerealizacaoException;
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

        ResultadoSimulacao resultado = calcularSimulacaoParaProduto(requestDTO, produto);

        Simulacao simulacao = montarSimulacaoEntity(requestDTO, resultado);

        simulacaoRepository.save(simulacao);
        resultado.setIdSimulacao(simulacao.getIdSimulacao());

        enviarParaEventHub(resultado);

        return new SimulacaoResponseDTO(resultado);
    }

    private void validarRequest(SimulacaoRequestDTO dto) {
        if (dto.getValorDesejado() == null || dto.getValorDesejado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("O valor desejado deve ser maior que zero.");
        }
        if (dto.getPrazo() <= 0) {
            throw new DadosInvalidosException("O prazo deve ser maior que 0.");
        }
    }

    private Produto buscarProduto(SimulacaoRequestDTO dto) {
        return produtoRepository.findByRange(dto.getValorDesejado(), dto.getPrazo())
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                        "Não foi encontrado produto que se encaixe nas condições de valor e prazo."));
    }

    private ResultadoSimulacao calcularSimulacaoParaProduto(SimulacaoRequestDTO dto, Produto produto) {
        return simuladorEmprestimo.simularEmprestimo(
                dto.getValorDesejado(),
                dto.getPrazo(),
                produto.getCoProduto(),
                produto.getNoProduto(),
                produto.getPcTaxaJuros()
        );
    }

    private Simulacao montarSimulacaoEntity(SimulacaoRequestDTO dto, ResultadoSimulacao resultado) {
        Simulacao simulacao = new Simulacao();
        simulacao.setCodigoProduto(resultado.getCodigoProduto());
        simulacao.setDescricaoProduto(resultado.getDescricaoProduto());
        simulacao.setTaxaJuros(resultado.getTaxaJuros());
        simulacao.setPrazo(dto.getPrazo());
        simulacao.setValorDesejado(dto.getValorDesejado());
        simulacao.setValorTotalSac(calcularValorTotalPorTipo("SAC", resultado));
        simulacao.setValorTotalPrice(calcularValorTotalPorTipo("PRICE", resultado));
        simulacao.setDataReferencia(LocalDate.now());

        try {
            simulacao.setResultadoSimulacaoJson(objectMapper.writeValueAsString(resultado.getResultadoSimulacao()));
        } catch (JsonProcessingException e) {
            throw new SerealizacaoException("Erro ao serializar resultado da simulação.");
        }
        return simulacao;
    }

    private BigDecimal calcularValorTotalPorTipo(String tipo, ResultadoSimulacao resultado) {
        return resultado.getResultadoSimulacao().stream()
                .filter(r -> tipo.equals(r.getTipo()))
                .flatMap(r -> r.getParcelas().stream())
                .map(p -> p.getValorPrestacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void enviarParaEventHub(ResultadoSimulacao resultado) {
        try {
            String json = objectMapper.writeValueAsString(resultado);
            eventHubService.enviarEvento(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao enviar evento para EventHub.", e);
        }
    }

    public Page<Simulacao> listarSimulacoes(Pageable pageable) {
        return simulacaoRepository.findAll(pageable);
    }

    public List<SimulacaoDetalhe> buscarSimulacoesPorDia(LocalDate data) {
        return simulacaoRepository.findSimulacoesByDay(data);
    }

    public List<SimulacaoDetalhe> buscarSimulacoesPorDiaEProduto(LocalDate data, int codigoProduto) {
        return simulacaoRepository.findSimulacoesByDataAndProduto(data, codigoProduto);
    }
}
