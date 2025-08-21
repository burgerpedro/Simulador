package br.gov.caixa.Simulador.model;

import java.math.BigDecimal;

public class Parcela {
    private int numero;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorPrestacao;

    public Parcela() {
    }

    public Parcela(int numero, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorPrestacao) {
        this.numero = numero;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorPrestacao = valorPrestacao;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public BigDecimal getValorAmortizacao() {
        return valorAmortizacao;
    }

    public void setValorAmortizacao(BigDecimal valorAmortizacao) {
        this.valorAmortizacao = valorAmortizacao;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public BigDecimal getValorPrestacao() {
        return valorPrestacao;
    }

    public void setValorPrestacao(BigDecimal valorPrestacao) {
        this.valorPrestacao = valorPrestacao;
    }
}
