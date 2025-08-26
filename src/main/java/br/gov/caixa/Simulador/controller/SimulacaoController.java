package br.gov.caixa.Simulador.controller;

import br.gov.caixa.Simulador.dto.*;
import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.model.request.SimulacaoDetalhe;
import br.gov.caixa.Simulador.service.SimulacaoService;
import br.gov.caixa.Simulador.service.TelemetriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Simula um empréstimo e persiste no banco de dados")
    @PostMapping("/simular")
    public ResponseEntity<SimulacaoResponseDTO> simularEmprestimo(
            @RequestBody SimulacaoRequestDTO requestDTO) {
        SimulacaoResponseDTO responseDTO = simulacaoService.simularEGravar(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Lista todas as simulações paginadas")
    @GetMapping("/simulacoes")
    public ResponseEntity<PaginatedSimulacaoResponseDTO> listarSimulacoes(
            @ParameterObject Pageable pageable) {
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

    @Operation(summary = "Lista simulações filtradas por data")
    @GetMapping("/simulacoes/por-dia")
    public ResponseEntity<SimulacoesPorDiaResponseDTO> listarSimulacoesPorDia(
            @Parameter(description = "Data da simulação (YYYY-MM-DD)", example = "2025-08-25")
            @NotNull(message = "A data não pode ser nula")
            @RequestParam("data")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<SimulacaoDetalhe> detalhes = simulacaoService.buscarSimulacoesPorDia(data);
        return ResponseEntity.ok(buildSimulacoesPorDiaResponse(data, detalhes));
    }

    @Operation(summary = "Lista simulações filtradas por data e código do produto")
    @GetMapping("/simulacoes/por-dia-e-produto")
    public ResponseEntity<SimulacoesPorDiaResponseDTO> listarSimulacoesPorDiaEProduto(
            @Parameter(description = "Data da simulação (YYYY-MM-DD)", example = "2025-08-25")
            @NotNull(message = "A data não pode ser nula")
            @RequestParam("data")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,

            @Parameter(description = "Código do produto", example = "1")
            @NotNull(message = "O código do produto não pode ser nulo")
            @Min(value = 1, message = "O código do produto deve ser maior que 0")
            @RequestParam("codigoProduto") Integer codigoProduto) {

        List<SimulacaoDetalhe> detalhes = simulacaoService.buscarSimulacoesPorDiaEProduto(data, codigoProduto);
        return ResponseEntity.ok(buildSimulacoesPorDiaResponse(data, detalhes));
    }

    @Operation(summary = "Health check da aplicação")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "O serviço de simulação está online e operando.");
        return ResponseEntity.ok(response);
    }

    private SimulacoesPorDiaResponseDTO buildSimulacoesPorDiaResponse(LocalDate data, List<SimulacaoDetalhe> detalhes) {
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
        return responseDTO;
    }
}
