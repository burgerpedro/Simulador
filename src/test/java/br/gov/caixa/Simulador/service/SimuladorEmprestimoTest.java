package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.model.ResultadoAmortizacao;
import br.gov.caixa.Simulador.model.ResultadoSimulacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class SimuladorEmprestimoTest {

    private final SimuladorEmprestimo simulador = new SimuladorEmprestimo();

    @Test
    void deveGerarSimulacaoComSacEPrice() {
        BigDecimal valor = new BigDecimal("1000.00");
        int prazo = 10;
        BigDecimal taxa = new BigDecimal("0.02");

        ResultadoSimulacao resultado =
                simulador.simularEmprestimo(valor, prazo, 123, "Produto Teste", taxa);

        assertThat(resultado.getCodigoProduto()).isEqualTo(123);
        assertThat(resultado.getDescricaoProduto()).isEqualTo("Produto Teste");
        assertThat(resultado.getResultadoSimulacao()).hasSize(2);

        ResultadoAmortizacao sac = resultado.getResultadoSimulacao()
                .stream().filter(r -> "SAC".equals(r.getTipo())).findFirst().orElseThrow();
        ResultadoAmortizacao price = resultado.getResultadoSimulacao()
                .stream().filter(r -> "PRICE".equals(r.getTipo())).findFirst().orElseThrow();

        assertThat(sac.getParcelas()).hasSize(10);
        assertThat(price.getParcelas()).hasSize(10);

        assertThat(sac.getParcelas().getLast().getValorAmortizacao()
                .add(sac.getParcelas().getLast().getValorJuros()))
                .isEqualByComparingTo(sac.getParcelas().getLast().getValorPrestacao());
    }
}
