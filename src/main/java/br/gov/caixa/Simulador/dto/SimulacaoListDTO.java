package br.gov.caixa.Simulador.dto;

import br.gov.caixa.Simulador.model.local.Simulacao;

import java.math.BigDecimal;

public class SimulacaoListDTO {
    private int idSimulacao;
    private BigDecimal valorDesejado;
    private int prazo;
    private BigDecimal valorTotalParcelasPrice;
    private BigDecimal valorTotalParcelasSac;

    public BigDecimal getValorTotalParcelasPrice() {
        return valorTotalParcelasPrice;
    }

    public void setValorTotalParcelasPrice(BigDecimal valorTotalParcelasPrice) {
        this.valorTotalParcelasPrice = valorTotalParcelasPrice;
    }

    public BigDecimal getValorTotalParcelasSac() {
        return valorTotalParcelasSac;
    }

    public void setValorTotalParcelasSac(BigDecimal valorTotalParcelasSac) {
        this.valorTotalParcelasSac = valorTotalParcelasSac;
    }

    public SimulacaoListDTO(Simulacao simulacao) {
        this.idSimulacao = simulacao.getIdSimulacao();
        this.valorDesejado = simulacao.getValorDesejado();
        this.prazo = simulacao.getPrazo();
        this.valorTotalParcelasPrice = simulacao.getValorTotalPrice();
        this.valorTotalParcelasSac = simulacao.getValorTotalSac();
    }

    public SimulacaoListDTO() {
    }

    public int getIdSimulacao() {
        return idSimulacao;
    }

    public void setIdSimulacao(int idSimulacao) {
        this.idSimulacao = idSimulacao;
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

}
