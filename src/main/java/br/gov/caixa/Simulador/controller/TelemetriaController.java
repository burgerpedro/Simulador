package br.gov.caixa.Simulador.controller;

import br.gov.caixa.Simulador.dto.TelemetriaResponseDTO;
import br.gov.caixa.Simulador.service.TelemetriaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simulador/v1/telemetria")
public class TelemetriaController {

    private final TelemetriaService telemetriaService;

    @Autowired
    public TelemetriaController(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    @GetMapping
    @Operation(summary = "Retorna métricas de telemetria")
    public ResponseEntity<TelemetriaResponseDTO> consultarTelemetria() {
        return ResponseEntity.ok(telemetriaService.obterDadosTelemetria());
    }
}

