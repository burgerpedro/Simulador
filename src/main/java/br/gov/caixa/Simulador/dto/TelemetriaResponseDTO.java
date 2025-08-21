package br.gov.caixa.Simulador.dto;

import br.gov.caixa.Simulador.model.telemetria.MetricasTelemetria;

import java.time.LocalDate;
import java.util.List;

public class TelemetriaResponseDTO {
    private LocalDate dataReferencia;
    private List<MetricasTelemetria> metricasTelemetrias ;

    public TelemetriaResponseDTO() {
    }

    public TelemetriaResponseDTO(LocalDate dataReferencia, List<MetricasTelemetria> metricasTelemetrias) {
        this.dataReferencia = dataReferencia;
        this.metricasTelemetrias = metricasTelemetrias;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public List<MetricasTelemetria> getMetricasTelemetrias() {
        return metricasTelemetrias;
    }

    public void setMetricasTelemetrias(List<MetricasTelemetria> metricasTelemetrias) {
        this.metricasTelemetrias = metricasTelemetrias;
    }
}
