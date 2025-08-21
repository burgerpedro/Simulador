package br.gov.caixa.Simulador.model.telemetria;

public class MetricasTelemetria {

    private String nomeApi;
    private long qtdRequisicoes;
    private long tempoMedio;
    private long tempoMinimo;
    private long tempoMaximo;
    private double percentualSucesso;

    public String getNomeApi() {
        return nomeApi;
    }

    public void setNomeApi(String nomeApi) {
        this.nomeApi = nomeApi;
    }

    public long getQtdRequisicoes() {
        return qtdRequisicoes;
    }

    public void setQtdRequisicoes(long qtdRequisicoes) {
        this.qtdRequisicoes = qtdRequisicoes;
    }

    public long getTempoMedio() {
        return tempoMedio;
    }

    public void setTempoMedio(long tempoMedio) {
        this.tempoMedio = tempoMedio;
    }

    public long getTempoMinimo() {
        return tempoMinimo;
    }

    public void setTempoMinimo(long tempoMinimo) {
        this.tempoMinimo = tempoMinimo;
    }

    public long getTempoMaximo() {
        return tempoMaximo;
    }

    public void setTempoMaximo(long tempoMaximo) {
        this.tempoMaximo = tempoMaximo;
    }

    public double getPercentualSucesso() {
        return percentualSucesso;
    }

    public void setPercentualSucesso(double percentualSucesso) {
        this.percentualSucesso = percentualSucesso;
    }
}
