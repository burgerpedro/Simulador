package br.gov.caixa.Simulador.dto;

import java.math.BigDecimal;

public class SimulacaoDetalheDTO {
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaMediaJuro;
    private BigDecimal valorMedioPrestacaoPrice;
    private BigDecimal valorMedioPrestacaoSac;
    private BigDecimal valorTotalDesejado;
    private BigDecimal valorTotalCreditoPrice;
    private BigDecimal valorTotalCreditoSac;

    public SimulacaoDetalheDTO(int codigoProduto, String descricaoProduto, BigDecimal taxaMediaJuro, BigDecimal valorMedioPrestacaoPrice, BigDecimal valorMedioPrestacaoSac, BigDecimal valorTotalDesejado, BigDecimal valorTotalCreditoPrice, BigDecimal valorTotalCreditoSac) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaMediaJuro = taxaMediaJuro;
        this.valorMedioPrestacaoPrice = valorMedioPrestacaoPrice;
        this.valorMedioPrestacaoSac = valorMedioPrestacaoSac;
        this.valorTotalDesejado = valorTotalDesejado;
        this.valorTotalCreditoPrice = valorTotalCreditoPrice;
        this.valorTotalCreditoSac = valorTotalCreditoSac;
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

    public BigDecimal getValorTotalCreditoPrice() {
        return valorTotalCreditoPrice;
    }

    public void setValorTotalCreditoPrice(BigDecimal valorTotalCreditoPrice) {
        this.valorTotalCreditoPrice = valorTotalCreditoPrice;
    }

    public BigDecimal getValorTotalCreditoSac() {
        return valorTotalCreditoSac;
    }

    public void setValorTotalCreditoSac(BigDecimal valorTotalCreditoSac) {
        this.valorTotalCreditoSac = valorTotalCreditoSac;
    }
}
