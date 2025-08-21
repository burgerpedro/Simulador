package br.gov.caixa.Simulador.model.local;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "SIMULACAO")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_simulacao")
    private int idSimulacao;

    @Column(name = "codigo_produto")
    private int codigoProduto;

    @Column(name = "descricao_produto")
    private String descricaoProduto;

    @Column(name = "taxa_juros", precision = 10, scale = 9)
    private BigDecimal taxaJuros;

    @Column(name = "resultado_simulacao", columnDefinition = "jsonb")
    @Type(JsonType.class)
    private String resultadoSimulacaoJson;

    @Column(name="vr_total_parcela")
    private BigDecimal valorTotalParcelas;

    @Column(name="prazo")
    private int prazo;

    @Column(name = "valor_desejado")
    private BigDecimal valorDesejado;

    @Column(name = "data_referencia")
    private LocalDate dataReferencia;

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

    public String getResultadoSimulacaoJson() {
        return resultadoSimulacaoJson;
    }

    public void setResultadoSimulacaoJson(String resultadoSimulacaoJson) {
        this.resultadoSimulacaoJson = resultadoSimulacaoJson;
    }

    public BigDecimal getValorTotalParcelas() {
        return valorTotalParcelas;
    }

    public void setValorTotalParcelas(BigDecimal valorTotalParcelas) {
        this.valorTotalParcelas = valorTotalParcelas;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }

    public BigDecimal getValorDesejado() {
        return valorDesejado;
    }

    public void setValorDesejado(BigDecimal valorDesejado) {
        this.valorDesejado = valorDesejado;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }
}
