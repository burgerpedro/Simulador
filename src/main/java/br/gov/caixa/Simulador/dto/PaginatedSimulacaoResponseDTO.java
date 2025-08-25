package br.gov.caixa.Simulador.dto;

import java.util.List;

public class PaginatedSimulacaoResponseDTO {
    private int pagina;
    private long qtdRegistros;
    private int qtdRegistrosPagina;
    private List<SimulacaoListDTO> registros;

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public long getQtdRegistros() {
        return qtdRegistros;
    }

    public void setQtdRegistros(long qtdRegistros) {
        this.qtdRegistros = qtdRegistros;
    }

    public int getQtdRegistrosPagina() {
        return qtdRegistrosPagina;
    }

    public void setQtdRegistrosPagina(int qtdRegistrosPagina) {
        this.qtdRegistrosPagina = qtdRegistrosPagina;
    }

    public List<SimulacaoListDTO> getRegistros() {
        return registros;
    }

    public void setRegistros(List<SimulacaoListDTO> registros) {
        this.registros = registros;
    }
}
