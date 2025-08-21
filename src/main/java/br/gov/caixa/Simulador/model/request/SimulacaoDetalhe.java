package br.gov.caixa.Simulador.model.request;

import java.math.BigDecimal;

public class SimulacaoDetalhe {

    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaMediaJuro;
    private BigDecimal valorMedioPrestacao;
    private BigDecimal valorTotalDesejado;
    private BigDecimal valorTotalCredito;

    public SimulacaoDetalhe(int codigoProduto, String descricaoProduto, BigDecimal taxaMediaJuro, BigDecimal valorMedioPrestacao, BigDecimal valorTotalDesejado, BigDecimal valorTotalCredito) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaMediaJuro = taxaMediaJuro;
        this.valorMedioPrestacao = valorMedioPrestacao;
        this.valorTotalDesejado = valorTotalDesejado;
        this.valorTotalCredito = valorTotalCredito;
    }

    public SimulacaoDetalhe() {
    }

    public SimulacaoDetalhe(int codigoProduto, String descricaoProduto, BigDecimal taxaMediaJuro, BigDecimal valorTotalCredito, BigDecimal valorTotalDesejado) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaMediaJuro = taxaMediaJuro;
        this.valorTotalCredito = valorTotalCredito;
        this.valorTotalDesejado = valorTotalDesejado;
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

    public BigDecimal getTaxaMediaJuro() {
        return taxaMediaJuro;
    }

    public void setTaxaMediaJuro(BigDecimal taxaMediaJuro) {
        this.taxaMediaJuro = taxaMediaJuro;
    }

    public BigDecimal getValorMedioPrestacao() {
        return valorMedioPrestacao;
    }

    public void setValorMedioPrestacao(BigDecimal valorMedioPrestacao) {
        this.valorMedioPrestacao = valorMedioPrestacao;
    }

    public BigDecimal getValorTotalDesejado() {
        return valorTotalDesejado;
    }

    public void setValorTotalDesejado(BigDecimal valorTotalDesejado) {
        this.valorTotalDesejado = valorTotalDesejado;
    }

    public BigDecimal getValorTotalCredito() {
        return valorTotalCredito;
    }

    public void setValorTotalCredito(BigDecimal valorTotalCredito) {
        this.valorTotalCredito = valorTotalCredito;
    }
}
