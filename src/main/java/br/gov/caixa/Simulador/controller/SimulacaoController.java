package br.gov.caixa.Simulador.controller;

import br.gov.caixa.Simulador.model.dto.*;
import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.model.request.SimulacaoDetalhe;
import br.gov.caixa.Simulador.service.SimulacaoService;
import br.gov.caixa.Simulador.service.TelemetriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/simulador")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @Autowired
    private TelemetriaService telemetriaService;

    @PostMapping("/simular")
    public ResponseEntity<SimulacaoResponseDTO> simularEmprestimo(@RequestBody SimulacaoRequestDTO requestDTO) {
        SimulacaoResponseDTO responseDTO = simulacaoService.simularEGravar(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/simulacoes")
    public ResponseEntity<Page<SimulacaoListDTO>> listarSimulacoes(Pageable pageable) {
        Page<Simulacao> simulacoesPage = simulacaoService.listarSimulacoes(pageable);
        Page<SimulacaoListDTO> dtoList = simulacoesPage.map(SimulacaoListDTO::new);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/simulacoes/por-dia/{data}")
    public ResponseEntity<SimulacoesPorDiaResponseDTO> listarSimulacoesPorDia(@PathVariable("data") String data) {
        LocalDate dataReferencia = LocalDate.parse(data);
        List<SimulacaoDetalhe> simulacoesDetalhe = simulacaoService.buscarSimulacoesPorDia(dataReferencia);
        List<SimulacaoDetalheDTO> dtoList = simulacoesDetalhe.stream()
                .map(sim -> new SimulacaoDetalheDTO(
                        sim.getCodigoProduto(),
                        sim.getDescricaoProduto(),
                        sim.getTaxaMediaJuro(),
                        sim.getValorMedioPrestacao(),
                        sim.getValorTotalDesejado(),
                        sim.getValorTotalCredito()
                ))
                .collect(Collectors.toList());

        SimulacoesPorDiaResponseDTO responseDTO = new SimulacoesPorDiaResponseDTO();
        responseDTO.setDataReferencia(dataReferencia);
        responseDTO.setSimulacoes(dtoList);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/telemetria")
    public ResponseEntity<TelemetriaResponseDTO> getTelemetria() {
        TelemetriaResponseDTO telemetryData = telemetriaService.getTelemetryData();
        return ResponseEntity.ok(telemetryData);
    }


    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "O serviço de simulação está online e operando.");
        return ResponseEntity.ok(response);
    }
}
