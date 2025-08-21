package br.gov.caixa.Simulador.dto;

import java.time.LocalDate;
import java.util.List;

public class SimulacoesPorDiaResponseDTO {
    private LocalDate dataReferencia;
    private List<SimulacaoDetalheDTO> simulacoes;

    public SimulacoesPorDiaResponseDTO() {
    }

    public SimulacoesPorDiaResponseDTO(LocalDate dataReferencia, List<SimulacaoDetalheDTO> simulacoes) {
        this.dataReferencia = dataReferencia;
        this.simulacoes = simulacoes;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public List<SimulacaoDetalheDTO> getSimulacoes() {
        return simulacoes;
    }

    public void setSimulacoes(List<SimulacaoDetalheDTO> simulacoes) {
        this.simulacoes = simulacoes;
    }
}
