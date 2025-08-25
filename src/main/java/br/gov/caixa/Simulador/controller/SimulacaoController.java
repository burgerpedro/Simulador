package br.gov.caixa.Simulador.controller;

import br.gov.caixa.Simulador.dto.*;
import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.model.request.SimulacaoDetalhe;
import br.gov.caixa.Simulador.service.SimulacaoService;
import br.gov.caixa.Simulador.service.TelemetriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/simulador/v1")
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
    public ResponseEntity<PaginatedSimulacaoResponseDTO> listarSimulacoes(Pageable pageable) {
        Page<Simulacao> simulacoesPage = simulacaoService.listarSimulacoes(pageable);

        List<SimulacaoListDTO> dtoList = simulacoesPage.getContent().stream()
                .map(SimulacaoListDTO::new)
                .collect(Collectors.toList());

        PaginatedSimulacaoResponseDTO response = new PaginatedSimulacaoResponseDTO();
        response.setPagina(simulacoesPage.getNumber() + 1);
        response.setQtdRegistros(simulacoesPage.getTotalElements());
        response.setQtdRegistrosPagina(simulacoesPage.getNumberOfElements());
        response.setRegistros(dtoList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/simulacoes/por-dia")
    public ResponseEntity<SimulacoesPorDiaResponseDTO> listarSimulacoesPorDia(
            @NotNull(message = "A data não pode ser nula ou vazia.")
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<SimulacaoDetalhe> detalhes = simulacaoService.buscarSimulacoesPorDia(data);
        List<SimulacaoDetalheDTO> dtoList = detalhes.stream()
                .map(sim -> new SimulacaoDetalheDTO(
                        sim.getCodigoProduto(),
                        sim.getDescricaoProduto(),
                        sim.getTaxaMediaJuro(),
                        sim.getValorMedioPrestacaoPrice(),
                        sim.getValorMedioPrestacaoSac(),
                        sim.getValorTotalDesejado(),
                        sim.getValorTotalPrice(),
                        sim.getValorTotalSac()
                ))
                .collect(Collectors.toList());

        SimulacoesPorDiaResponseDTO responseDTO = new SimulacoesPorDiaResponseDTO();
        responseDTO.setDataReferencia(data);
        responseDTO.setSimulacoes(dtoList);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/simulacoes/por-dia-e-produto")
    public ResponseEntity<SimulacoesPorDiaResponseDTO> listarSimulacoesPorDiaEProduto(
            @NotNull(message = "A data não pode ser vazia ou nula.")
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,

            @Min(value = 1, message = "O código do produto não pode ser vazio ou nulo.")
            @RequestParam("codigoProduto") int codigoProduto) {

        List<SimulacaoDetalhe> simulacoesDetalhe = simulacaoService.buscarSimulacoesPorDiaEProduto(data, codigoProduto);

        List<SimulacaoDetalheDTO> dtoList = simulacoesDetalhe.stream()
                .map(sim -> new SimulacaoDetalheDTO(
                        sim.getCodigoProduto(),
                        sim.getDescricaoProduto(),
                        sim.getTaxaMediaJuro(),
                        sim.getValorMedioPrestacaoPrice(),
                        sim.getValorMedioPrestacaoSac(),
                        sim.getValorTotalDesejado(),
                        sim.getValorTotalPrice(),
                        sim.getValorTotalSac()
                ))
                .collect(Collectors.toList());

        SimulacoesPorDiaResponseDTO responseDTO = new SimulacoesPorDiaResponseDTO();
        responseDTO.setDataReferencia(data);
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
