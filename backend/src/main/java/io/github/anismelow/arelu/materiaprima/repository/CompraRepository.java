package io.github.anismelow.arelu.materiaprima.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.anismelow.arelu.materiaprima.model.Compra;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    /**
     * Historial de un ingrediente, de la compra más reciente a la más antigua.
     * El @EntityGraph trae el ingrediente y la tienda en la MISMA consulta (evita el problema N+1).
     */
    @EntityGraph(attributePaths = {"ingrediente", "tienda"})
    List<Compra> findByIngredienteIdOrderByFechaDescIdDesc(Long ingredienteId);

}
