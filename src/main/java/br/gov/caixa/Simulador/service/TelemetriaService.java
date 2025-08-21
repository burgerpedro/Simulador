package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.dto.TelemetriaResponseDTO;
import br.gov.caixa.Simulador.model.telemetria.MetricasTelemetria;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TelemetriaService {

    private final ConcurrentHashMap<String, EndpointStats> endpointStats = new ConcurrentHashMap<>();

    public void updateMetrics(String endpointName, long duration) {
        // Usa computeIfAbsent para garantir que o objeto EndpointStats existe antes de atualizar
        endpointStats.computeIfAbsent(endpointName, k -> new EndpointStats()).addCall(duration);
    }

    public TelemetriaResponseDTO getTelemetryData() {
        TelemetriaResponseDTO responseDTO = new TelemetriaResponseDTO();
        responseDTO.setDataReferencia(LocalDate.now());

        List<MetricasTelemetria> metricsList = new ArrayList<>();
        endpointStats.forEach((endpointName, stats) -> {
            MetricasTelemetria metrics = new MetricasTelemetria();
            metrics.setNomeApi(endpointName);
            metrics.setQtdRequisicoes(stats.getCallCount());
            metrics.setTempoMedio((long) stats.getAverageTime());
            metrics.setTempoMinimo(stats.getMinTime());
            metrics.setTempoMaximo(stats.getMaxTime());
            // A lógica de percentual de sucesso é adicionada aqui para preencher o DTO
            metrics.setPercentualSucesso(stats.getSuccessPercentage());
            metricsList.add(metrics);
        });

        responseDTO.setMetricasTelemetrias(metricsList);
        return responseDTO;
    }

    public static class EndpointStats {
        private int callCount = 0;
        private int successCount = 0;
        private long totalTime = 0;
        private long minTime = Long.MAX_VALUE;
        private long maxTime = Long.MIN_VALUE;

        public synchronized void addCall(long timeInMs) {
            callCount++;
            totalTime += timeInMs;
            if (timeInMs < minTime) {
                minTime = timeInMs;
            }
            if (timeInMs > maxTime) {
                maxTime = timeInMs;
            }

            successCount++;
        }

        public int getCallCount() { return callCount; }
        public double getAverageTime() {
            return callCount > 0 ? (double) totalTime / callCount : 0.0;
        }
        public long getMinTime() { return minTime; }
        public long getMaxTime() { return maxTime; }
        public int getSuccessPercentage() {
            return callCount > 0 ? (successCount * 100) / callCount : 0;
        }
    }
}
