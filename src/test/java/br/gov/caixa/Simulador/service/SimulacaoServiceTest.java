package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.dto.SimulacaoRequestDTO;
import br.gov.caixa.Simulador.dto.SimulacaoResponseDTO;
import br.gov.caixa.Simulador.exception.DadosInvalidosException;
import br.gov.caixa.Simulador.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.Simulador.model.external.Produto;
import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.repository.external.ProdutoRepository;
import br.gov.caixa.Simulador.repository.local.SimulacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimulacaoServiceTest {

    @Mock private SimuladorEmprestimo simuladorEmprestimo;
    @Mock private SimulacaoRepository simulacaoRepository;
    @Mock private ProdutoRepository produtoRepository;
    @Mock private EventHubService eventHubService;

    @InjectMocks
    private SimulacaoService simulacaoService;

    private SimulacaoRequestDTO request;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        request = new SimulacaoRequestDTO(new BigDecimal("1000"), 12);
    }

    @Test
    void deveLancarExcecaoQuandoValorMenorOuIgualAZero() {
        request.setValorDesejado(BigDecimal.ZERO);

        assertThatThrownBy(() -> simulacaoService.simularEGravar(request))
                .isInstanceOf(DadosInvalidosException.class)
                .hasMessageContaining("O valor desejado deve ser maior que zero.");
    }

    @Test
    void deveLancarExcecaoQuandoPrazoMenorOuIgualZero() {
        request.setPrazo(0);

        assertThatThrownBy(() -> simulacaoService.simularEGravar(request))
                .isInstanceOf(DadosInvalidosException.class)
                .hasMessageContaining("O prazo deve ser maior que 0.");
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findByRange(any(), anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> simulacaoService.simularEGravar(request))
                .isInstanceOf(ProdutoNaoEncontradoException.class);
    }

    @Test
    void deveSalvarSimulacaoComSucesso() {
        Produto produto = new Produto();
        produto.setCoProduto(123);
        produto.setNoProduto("Produto Teste");
        produto.setPcTaxaJuros(new BigDecimal("0.02"));

        when(produtoRepository.findByRange(any(), anyInt())).thenReturn(Optional.of(produto));
        when(simuladorEmprestimo.simularEmprestimo(any(), anyInt(), anyInt(), anyString(), any()))
                .thenCallRealMethod();
        when(simulacaoRepository.save(any(Simulacao.class))).thenAnswer(invocation -> {
            Simulacao s = invocation.getArgument(0);
            s.setIdSimulacao(999);
            return s;
        });

        SimulacaoResponseDTO response = simulacaoService.simularEGravar(request);

        verify(simulacaoRepository, times(1)).save(any(Simulacao.class));
        verify(eventHubService, times(1)).enviarEvento(anyString());

        assertThat(response.getCodigoProduto()).isEqualTo(123);
        assertThat(response.getDescricaoProduto()).isEqualTo("Produto Teste");
    }
}
