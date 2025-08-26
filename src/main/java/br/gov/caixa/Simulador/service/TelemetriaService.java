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

    private final ConcurrentHashMap<String, EstatisticasEndpoint> estatisticasPorEndpoint = new ConcurrentHashMap<>();

    public void atualizarMetricas(String nomeEndpoint, long duracao, int statusHttp) {
        estatisticasPorEndpoint
                .computeIfAbsent(nomeEndpoint, k -> new EstatisticasEndpoint())
                .adicionarChamada(duracao, statusHttp);
    }

    public TelemetriaResponseDTO obterDadosTelemetria() {
        TelemetriaResponseDTO responseDTO = new TelemetriaResponseDTO();
        responseDTO.setDataReferencia(LocalDate.now());

        List<MetricasTelemetria> listaMetricas = new ArrayList<>();
        estatisticasPorEndpoint.forEach((nomeEndpoint, estatisticas) -> {
            MetricasTelemetria metricas = new MetricasTelemetria();
            metricas.setNomeApi(nomeEndpoint);
            metricas.setQtdRequisicoes(estatisticas.getQtdChamadas());
            metricas.setTempoMedio((long) estatisticas.getTempoMedio());
            metricas.setTempoMinimo(estatisticas.getTempoMinimo());
            metricas.setTempoMaximo(estatisticas.getTempoMaximo());
            metricas.setPercentualSucesso(estatisticas.getPercentualSucesso());
            listaMetricas.add(metricas);
        });

        responseDTO.setMetricasTelemetrias(listaMetricas);
        return responseDTO;
    }

    public static class EstatisticasEndpoint {
        private int qtdChamadas = 0;
        private int qtdSucessos = 0;
        private long tempoTotal = 0;
        private long tempoMinimo = Long.MAX_VALUE;
        private long tempoMaximo = Long.MIN_VALUE;

        public synchronized void adicionarChamada(long tempoMs, int statusHttp) {
            qtdChamadas++;
            tempoTotal += tempoMs;

            if (tempoMs < tempoMinimo) {
                tempoMinimo = tempoMs;
            }
            if (tempoMs > tempoMaximo) {
                tempoMaximo = tempoMs;
            }

            if (statusHttp >= 200 && statusHttp < 300) {
                qtdSucessos++;
            }
        }

        public int getQtdChamadas() { return qtdChamadas; }

        public double getTempoMedio() {
            return qtdChamadas > 0 ? (double) tempoTotal / qtdChamadas : 0.0;
        }

        public long getTempoMinimo() { return tempoMinimo == Long.MAX_VALUE ? 0 : tempoMinimo; }

        public long getTempoMaximo() { return tempoMaximo == Long.MIN_VALUE ? 0 : tempoMaximo; }

        public int getPercentualSucesso() {
            return qtdChamadas > 0 ? (qtdSucessos * 100) / qtdChamadas : 0;
        }
    }
}
