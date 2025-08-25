package br.gov.caixa.Simulador.model.request;

import java.math.BigDecimal;

public class SimulacaoDetalhe {

    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaMediaJuro;
    private BigDecimal valorMedioPrestacaoPrice;
    private BigDecimal valorMedioPrestacaoSac;
    private BigDecimal valorTotalDesejado;
    private BigDecimal valorTotalPrice;
    private BigDecimal valorTotalSac;

    public SimulacaoDetalhe(int codigoProduto, String descricaoProduto, BigDecimal taxaMediaJuro, BigDecimal valorMedioPrestacaoPrice, BigDecimal valorMedioPrestacaoSac, BigDecimal valorTotalDesejado, BigDecimal valorTotalPrice, BigDecimal valorTotalSac) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaMediaJuro = taxaMediaJuro;
        this.valorMedioPrestacaoPrice = valorMedioPrestacaoPrice;
        this.valorMedioPrestacaoSac = valorMedioPrestacaoSac;
        this.valorTotalDesejado = valorTotalDesejado;
        this.valorTotalPrice = valorTotalPrice;
        this.valorTotalSac = valorTotalSac;
    }

    public BigDecimal getValorMedioPrestacaoPrice() {
        return valorMedioPrestacaoPrice;
    }

    public void setValorMedioPrestacaoPrice(BigDecimal valorMedioPrestacaoPrice) {
        this.valorMedioPrestacaoPrice = valorMedioPrestacaoPrice;
    }

    public BigDecimal getValorMedioPrestacaoSac() {
        return valorMedioPrestacaoSac;
    }

    public void setValorMedioPrestacaoSac(BigDecimal valorMedioPrestacaoSac) {
        this.valorMedioPrestacaoSac = valorMedioPrestacaoSac;
    }

    public SimulacaoDetalhe() {
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


    public BigDecimal getValorTotalDesejado() {
        return valorTotalDesejado;
    }

    public void setValorTotalDesejado(BigDecimal valorTotalDesejado) {
        this.valorTotalDesejado = valorTotalDesejado;
    }

    public BigDecimal getValorTotalPrice() {
        return valorTotalPrice;
    }

    public void setValorTotalPrice(BigDecimal valorTotalPrice) {
        this.valorTotalPrice = valorTotalPrice;
    }

    public BigDecimal getValorTotalSac() {
        return valorTotalSac;
    }

    public void setValorTotalSac(BigDecimal valorTotalSac) {
        this.valorTotalSac = valorTotalSac;
    }
}
