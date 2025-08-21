package br.gov.caixa.Simulador.repository.external;

import br.gov.caixa.Simulador.model.external.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Integer> {

    @Query("SELECT p FROM Produto p WHERE " +
            ":valor BETWEEN p.vrMinimo AND COALESCE(p.vrMaximo, :valor) AND " +
            ":prazo BETWEEN p.nuMinimoMeses AND COALESCE(p.nuMaximoMeses, :prazo)")
    Optional<Produto> findByRange(@Param("valor") BigDecimal valor, @Param("prazo") int prazo);
}
