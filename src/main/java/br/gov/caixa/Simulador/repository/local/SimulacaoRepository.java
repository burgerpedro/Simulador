package br.gov.caixa.Simulador.repository.local;

import br.gov.caixa.Simulador.model.local.Simulacao;
import br.gov.caixa.Simulador.model.request.SimulacaoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao,Integer> {
    @Query("""
        SELECT new br.gov.caixa.Simulador.model.request.SimulacaoDetalhe(
            s.codigoProduto,
            s.descricaoProduto,
            CAST(AVG(s.taxaJuros) AS big_decimal),
            CAST(AVG(s.valorTotalParcelas) AS big_decimal),
            CAST(SUM(s.valorDesejado) AS big_decimal),
            CAST(SUM(s.valorTotalParcelas) AS big_decimal)
        )
        FROM Simulacao s
        WHERE s.dataReferencia = :dataReferencia
        GROUP BY s.codigoProduto, s.descricaoProduto
    """)
    List<SimulacaoDetalhe> findSimulacoesByDay(@Param("dataReferencia") LocalDate dataReferencia);
}