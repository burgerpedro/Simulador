package br.gov.caixa.Simulador.model.request;

import java.time.LocalDate;
import java.util.List;

public class SimulacaoDia {

    private LocalDate dataReferencia;
    private List<SimulacaoDetalhe> simulacoes;

    public SimulacaoDia(LocalDate dataReferencia, List<SimulacaoDetalhe> simulacoes) {
        this.dataReferencia = dataReferencia;
        this.simulacoes = simulacoes;
    }


    public SimulacaoDia() {
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public List<SimulacaoDetalhe> getSimulacoes() {
        return simulacoes;
    }

    public void setSimulacoes(List<SimulacaoDetalhe> simulacoes) {
        this.simulacoes = simulacoes;
    }
}
