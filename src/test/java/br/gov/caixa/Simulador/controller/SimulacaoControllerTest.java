package br.gov.caixa.Simulador.controller;

import br.gov.caixa.Simulador.dto.PaginatedSimulacaoResponseDTO;
import br.gov.caixa.Simulador.dto.TelemetriaResponseDTO;
import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.model.request.SimulacaoDetalhe;
import br.gov.caixa.Simulador.service.SimulacaoService;
import br.gov.caixa.Simulador.service.TelemetriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SimulacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulacaoService simulacaoService;

    @MockBean
    private TelemetriaService telemetriaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarListaPaginadaDeSimulacoes() throws Exception {
        Simulacao simulacaoFalsa = new Simulacao();
        Page<Simulacao> paginaDeSimulacoes = new PageImpl<>(List.of(simulacaoFalsa));
        when(simulacaoService.listarSimulacoes(any(Pageable.class))).thenReturn(paginaDeSimulacoes);

        MvcResult result = mockMvc.perform(get("/simulador/v1/simulacoes")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PaginatedSimulacaoResponseDTO responseDTO =
                objectMapper.readValue(responseBody, PaginatedSimulacaoResponseDTO.class);

        assertThat(responseDTO.getPagina()).isEqualTo(1);
        assertThat(responseDTO.getQtdRegistros()).isEqualTo(1);
        assertThat(responseDTO.getQtdRegistrosPagina()).isEqualTo(1);
        assertThat(responseDTO.getRegistros()).hasSize(1);
    }

    @Test
    void deveRetornarSimulacoesPorDiaEProduto() throws Exception {
        LocalDate dataTeste = LocalDate.of(2025, 8, 26);
        int codigoProdutoTeste = 123;

        SimulacaoDetalhe detalheFalso = new SimulacaoDetalhe(
                codigoProdutoTeste, "Produto de Teste",
                new BigDecimal("0.02"), new BigDecimal("99.50"),
                new BigDecimal("98.75"), new BigDecimal("1000.00"),
                new BigDecimal("1194.00"), new BigDecimal("1185.00")
        );

        when(simulacaoService.buscarSimulacoesPorDiaEProduto(dataTeste, codigoProdutoTeste))
                .thenReturn(List.of(detalheFalso));

        mockMvc.perform(get("/simulador/v1/simulacoes/por-dia-e-produto")
                        .param("data", "2025-08-26")
                        .param("codigoProduto", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataReferencia").value("2025-08-26"))
                .andExpect(jsonPath("$.simulacoes[0].codigoProduto").value(123))
                .andExpect(jsonPath("$.simulacoes[0].descricaoProduto").value("Produto de Teste"));
    }

    @Test
    void deveRetornarTelemetria() throws Exception {
        when(telemetriaService.obterDadosTelemetria()).thenReturn(new TelemetriaResponseDTO());

        mockMvc.perform(get("/simulador/v1/telemetria"))
                .andExpect(status().isOk());
    }
}
