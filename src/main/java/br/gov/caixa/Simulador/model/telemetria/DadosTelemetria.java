package br.gov.caixa.Simulador.model.telemetria;

import java.time.LocalDate;
import java.util.List;

public class DadosTelemetria {
    private LocalDate dataReferencia;
    private List<MetricasTelemetria> listaEndpoints;

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public List<MetricasTelemetria> getListaEndpoints() {
        return listaEndpoints;
    }

    public void setListaEndpoints(List<MetricasTelemetria> listaEndpoints) {
        this.listaEndpoints = listaEndpoints;
    }
}
