package br.gov.caixa.Simulador.model;

import java.util.List;

public class ResultadoAmortizacao {
    private String tipo;
    private List<Parcela> parcelas;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }
}
