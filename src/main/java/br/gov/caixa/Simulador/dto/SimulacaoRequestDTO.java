package br.gov.caixa.Simulador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class SimulacaoRequestDTO {

    @NotNull(message = "O valor desejado não pode ser nulo")
    @DecimalMin(value = "200.00", message = "O valor minimo e R$200,00")
    @Schema(description = "Valor pretendido no financiamento", example = "900")
    private BigDecimal valorDesejado;

    @Min(value = 0, message = "O prazo minimo e 0 meses")
    @Schema(description = "Prazo em meses", example = "5")
    private int prazo;

    public SimulacaoRequestDTO(BigDecimal valorDesejado, int prazo) {
        this.valorDesejado = valorDesejado;
        this.prazo = prazo;
    }

    public SimulacaoRequestDTO() {
    }

    public BigDecimal getValorDesejado() {
        return valorDesejado;
    }

    public void setValorDesejado(BigDecimal valorDesejado) {
        this.valorDesejado = valorDesejado;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }
}
