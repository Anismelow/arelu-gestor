package io.github.anismelow.arelu.materiaprima;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    boolean existsByNombreIgnoreCase(String nombre);

    List<Ingrediente> findAllByOrderByNombreAsc();

}
