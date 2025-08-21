package br.gov.caixa.Simulador.model;

import java.math.BigDecimal;

public class EntradaSimulacao {
    private BigDecimal valorDesejado;
    private int prazo;

    public EntradaSimulacao() {
    }

    public EntradaSimulacao(BigDecimal valorDesejado, int prazo) {
        this.valorDesejado = valorDesejado;
        this.prazo = prazo;
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
