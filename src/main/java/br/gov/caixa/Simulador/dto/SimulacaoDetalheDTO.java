package br.gov.caixa.Simulador.dto;

import java.math.BigDecimal;

public class SimulacaoDetalheDTO {
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal mediaTaxaJuros;
    private BigDecimal mediaValorTotalParcelas;
    private BigDecimal somaValorDesejado;
    private BigDecimal somaValorTotalParcelas;

    public SimulacaoDetalheDTO(int codigoProduto, String descricaoProduto, BigDecimal mediaTaxaJuros, BigDecimal mediaValorTotalParcelas, BigDecimal somaValorDesejado, BigDecimal somaValorTotalParcelas) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.mediaTaxaJuros = mediaTaxaJuros;
        this.mediaValorTotalParcelas = mediaValorTotalParcelas;
        this.somaValorDesejado = somaValorDesejado;
        this.somaValorTotalParcelas = somaValorTotalParcelas;
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

    public BigDecimal getMediaTaxaJuros() {
        return mediaTaxaJuros;
    }

    public void setMediaTaxaJuros(BigDecimal mediaTaxaJuros) {
        this.mediaTaxaJuros = mediaTaxaJuros;
    }

    public BigDecimal getMediaValorTotalParcelas() {
        return mediaValorTotalParcelas;
    }

    public void setMediaValorTotalParcelas(BigDecimal mediaValorTotalParcelas) {
        this.mediaValorTotalParcelas = mediaValorTotalParcelas;
    }

    public BigDecimal getSomaValorDesejado() {
        return somaValorDesejado;
    }

    public void setSomaValorDesejado(BigDecimal somaValorDesejado) {
        this.somaValorDesejado = somaValorDesejado;
    }

    public BigDecimal getSomaValorTotalParcelas() {
        return somaValorTotalParcelas;
    }

    public void setSomaValorTotalParcelas(BigDecimal somaValorTotalParcelas) {
        this.somaValorTotalParcelas = somaValorTotalParcelas;
    }
}
