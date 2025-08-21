package br.gov.caixa.Simulador.model.external;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTO", schema = "dbo")
public class Produto {

    @Id
    @Column(name = "CO_PRODUTO")
    private int coProduto;

    @Column(name = "NO_PRODUTO")
    private String noProduto;

    @Column(name = "PC_TAXA_JUROS")
    private BigDecimal pcTaxaJuros;

    @Column(name = "NU_MINIMO_MESES")
    private int nuMinimoMeses;

    @Column(name = "NU_MAXIMO_MESES")
    private int nuMaximoMeses;

    @Column(name = "VR_MINIMO")
    private BigDecimal vrMinimo;

    @Column(name = "VR_MAXIMO")
    private BigDecimal vrMaximo;

    public int getCoProduto() {
        return coProduto;
    }

    public void setCoProduto(int coProduto) {
        this.coProduto = coProduto;
    }

    public String getNoProduto() {
        return noProduto;
    }

    public void setNoProduto(String noProduto) {
        this.noProduto = noProduto;
    }

    public BigDecimal getPcTaxaJuros() {
        return pcTaxaJuros;
    }

    public void setPcTaxaJuros(BigDecimal pcTaxaJuros) {
        this.pcTaxaJuros = pcTaxaJuros;
    }

    public int getNuMinimoMeses() {
        return nuMinimoMeses;
    }

    public void setNuMinimoMeses(int nuMinimoMeses) {
        this.nuMinimoMeses = nuMinimoMeses;
    }

    public int getNuMaximoMeses() {
        return nuMaximoMeses;
    }

    public void setNuMaximoMeses(int nuMaximoMeses) {
        this.nuMaximoMeses = nuMaximoMeses;
    }

    public BigDecimal getVrMinimo() {
        return vrMinimo;
    }

    public void setVrMinimo(BigDecimal vrMinimo) {
        this.vrMinimo = vrMinimo;
    }

    public BigDecimal getVrMaximo() {
        return vrMaximo;
    }

    public void setVrMaximo(BigDecimal vrMaximo) {
        this.vrMaximo = vrMaximo;
    }
}
