package io.github.anismelow.arelu.materiaprima.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.anismelow.arelu.materiaprima.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    boolean existsByNombreIgnoreCase(String nombre);

    List<Ingrediente> findAllByOrderByNombreAsc();

}
