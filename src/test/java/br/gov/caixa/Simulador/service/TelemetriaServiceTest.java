package br.gov.caixa.Simulador.service;

import br.gov.caixa.Simulador.dto.TelemetriaResponseDTO;
import br.gov.caixa.Simulador.model.telemetria.MetricasTelemetria;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TelemetriaServiceTest {

    private final TelemetriaService service = new TelemetriaService();

    @Test
    void deveRegistrarChamadasComMetricasCorretas() {

        service.atualizarMetricas("API_TESTE", 100, 200);
        service.atualizarMetricas("API_TESTE", 200, 201);
        service.atualizarMetricas("API_TESTE", 300, 500);

        TelemetriaResponseDTO dto = service.obterDadosTelemetria();
        List<MetricasTelemetria> metricas = dto.getMetricasTelemetrias();

        assertThat(metricas).hasSize(1);
        MetricasTelemetria m = metricas.getFirst();

        assertThat(m.getNomeApi()).isEqualTo("API_TESTE");
        assertThat(m.getQtdRequisicoes()).isEqualTo(3);
        assertThat(m.getTempoMinimo()).isEqualTo(100);
        assertThat(m.getTempoMaximo()).isEqualTo(300);
        assertThat(m.getTempoMedio()).isBetween(190L, 210L);
        assertThat(m.getPercentualSucesso()).isEqualTo(66);
    }
}
