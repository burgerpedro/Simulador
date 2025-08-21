package br.gov.caixa.Simulador.model.dto;

import java.math.BigDecimal;

public class SimulacaoRequestDTO {
    private BigDecimal valorDesejado;
    private int prazo;

    public SimulacaoRequestDTO(BigDecimal valorDesejado, int prazo) {
        this.valorDesejado = valorDesejado;
        this.prazo = prazo;
    }

    public SimulacaoRequestDTO() {
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
