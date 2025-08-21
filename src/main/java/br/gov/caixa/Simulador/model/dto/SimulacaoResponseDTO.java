package br.gov.caixa.Simulador.model.dto;

import br.gov.caixa.Simulador.model.ResultadoAmortizacao;
import br.gov.caixa.Simulador.model.ResultadoSimulacao;

import java.math.BigDecimal;
import java.util.List;

public class SimulacaoResponseDTO {
    private int idSimulacao;
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaJuros;
    private List<ResultadoAmortizacao> resultadoAmortizacao;

    public SimulacaoResponseDTO(ResultadoSimulacao resultado) {
        this.idSimulacao = resultado.getIdSimulacao();
        this.codigoProduto = resultado.getCodigoProduto();
        this.descricaoProduto = resultado.getDescricaoProduto();
        this.taxaJuros = resultado.getTaxaJuros();
        this.resultadoAmortizacao = resultado.getResultadoAmortizacao();
    }

    public int getIdSimulacao() {
        return idSimulacao;
    }

    public void setIdSimulacao(int idSimulacao) {
        this.idSimulacao = idSimulacao;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(int codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public List<ResultadoAmortizacao> getResultadoAmortizacao() {
        return resultadoAmortizacao;
    }

    public void setResultadoAmortizacao(List<ResultadoAmortizacao> resultadoAmortizacao) {
        this.resultadoAmortizacao = resultadoAmortizacao;
    }
}
