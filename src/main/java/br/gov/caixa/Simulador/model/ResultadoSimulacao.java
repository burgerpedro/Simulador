package br.gov.caixa.Simulador.model;

import java.math.BigDecimal;
import java.util.List;

public class ResultadoSimulacao {
    private int idSimulacao;
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaJuros;
    private List<ResultadoAmortizacao> resultadoSimulacao;

    public int getIdSimulacao() {
        return idSimulacao;
    }

    public void setIdSimulacao(int idSimulacao) {
        this.idSimulacao = idSimulacao;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public List<ResultadoAmortizacao> getResultadoSimulacao() {
        return resultadoSimulacao;
    }

    public void setResultadoSimulacao(List<ResultadoAmortizacao> resultadoSimulacao) {
        this.resultadoSimulacao = resultadoSimulacao;
    }
}
