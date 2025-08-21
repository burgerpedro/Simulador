package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.model.EntradaSimulacao;
import br.gov.caixa.Simulador.model.Parcela;
import br.gov.caixa.Simulador.model.ResultadoAmortizacao;
import br.gov.caixa.Simulador.model.ResultadoSimulacao;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimuladorEmprestimo {

    public ResultadoSimulacao simularEmprestimo(BigDecimal valorDesejado, int prazo, int codigoProduto, String descricaoProduto, BigDecimal taxaJuros){

        ResultadoSimulacao resultado = new ResultadoSimulacao();
        resultado.setCodigoProduto(codigoProduto);
        resultado.setDescricaoProduto(descricaoProduto);
        resultado.setTaxaJuros(taxaJuros);

        List<ResultadoAmortizacao> resultados = new ArrayList<>();
        resultados.add(calcularSac(valorDesejado, prazo, taxaJuros));
        resultados.add(calcularPrice(valorDesejado, prazo, taxaJuros));


        resultado.setResultadoSimulacao(resultados);

        return resultado;
    }

    private ResultadoAmortizacao calcularPrice(BigDecimal valorFinanciado, int prazoMeses, BigDecimal taxaMensal) {
        BigDecimal taxaComposta = taxaMensal.add(BigDecimal.ONE).pow(prazoMeses);
        BigDecimal valorPrestacao = valorFinanciado
                .multiply(taxaComposta)
                .multiply(taxaMensal)
                .divide(taxaComposta.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        ResultadoAmortizacao resultado = new ResultadoAmortizacao();
        resultado.setTipo("PRICE");
        List<Parcela> parcelas = new ArrayList<>();
        BigDecimal saldoDevedor = valorFinanciado;

        for (int i = 1; i <= prazoMeses; i++) {
            BigDecimal valorJuros = saldoDevedor.multiply(taxaMensal).setScale(2, RoundingMode.HALF_UP);
            BigDecimal valorAmortizacao = valorPrestacao.subtract(valorJuros);
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);

            if (i == prazoMeses) {
                valorAmortizacao = valorAmortizacao.add(saldoDevedor);
                saldoDevedor = BigDecimal.ZERO;
                valorPrestacao = valorAmortizacao.add(valorJuros);
            }

            Parcela parcela = new Parcela();
            parcela.setNumero(i);
            parcela.setValorPrestacao(valorPrestacao);
            parcela.setValorJuros(valorJuros);
            parcela.setValorAmortizacao(valorAmortizacao);
            parcelas.add(parcela);
        }

        resultado.setParcelas(parcelas);
        return resultado;
    }

    private ResultadoAmortizacao calcularSac(BigDecimal valorFinanciado, int prazoMeses, BigDecimal taxaMensal) {
        BigDecimal valorAmortizacao = valorFinanciado.divide(new BigDecimal(prazoMeses), 2, RoundingMode.HALF_UP);

        ResultadoAmortizacao resultado = new ResultadoAmortizacao();
        resultado.setTipo("SAC");
        List<Parcela> parcelas = new ArrayList<>();
        BigDecimal saldoDevedor = valorFinanciado;

        for (int i = 1; i <= prazoMeses; i++) {
            BigDecimal valorJuros = saldoDevedor.multiply(taxaMensal).setScale(2, RoundingMode.HALF_UP);
            BigDecimal valorPrestacao = valorAmortizacao.add(valorJuros);
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);

            if (i == prazoMeses && saldoDevedor.compareTo(BigDecimal.ZERO) != 0) {
                valorAmortizacao = valorAmortizacao.add(saldoDevedor);
                valorPrestacao = valorAmortizacao.add(valorJuros);
                saldoDevedor = BigDecimal.ZERO;
            }

            Parcela parcela = new Parcela();
            parcela.setNumero(i);
            parcela.setValorPrestacao(valorPrestacao);
            parcela.setValorJuros(valorJuros);
            parcela.setValorAmortizacao(valorAmortizacao);
            parcelas.add(parcela);
        }

        resultado.setParcelas(parcelas);
        return resultado;
    }
}
