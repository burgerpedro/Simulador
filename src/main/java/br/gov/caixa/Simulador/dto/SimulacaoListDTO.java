package br.gov.caixa.Simulador.dto;

import br.gov.caixa.Simulador.model.local.Simulacao;

import java.math.BigDecimal;

public class SimulacaoListDTO {
    private int idSimulacao;
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal valorDesejado;
    private int prazo;
    private BigDecimal valorTotalParcelas;

    public SimulacaoListDTO(Simulacao simulacao) {
        this.idSimulacao = simulacao.getIdSimulacao();
        this.codigoProduto = simulacao.getCodigoProduto();
        this.descricaoProduto = simulacao.getDescricaoProduto();
        this.valorDesejado = simulacao.getValorDesejado();
        this.prazo = simulacao.getPrazo();
        this.valorTotalParcelas = simulacao.getValorTotalParcelas();
    }

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

    public BigDecimal getValorDesejado() {
        return valorDesejado;
    }

    public void setValorDesejado(BigDecimal valorDesejado) {
        this.valorDesejado = valorDesejado;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public BigDecimal getValorTotalParcelas() {
        return valorTotalParcelas;
    }

    public void setValorTotalParcelas(BigDecimal valorTotalParcelas) {
        this.valorTotalParcelas = valorTotalParcelas;
    }
}
