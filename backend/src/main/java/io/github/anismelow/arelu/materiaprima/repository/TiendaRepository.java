package io.github.anismelow.arelu.materiaprima.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.anismelow.arelu.materiaprima.model.Tienda;

public interface TiendaRepository extends JpaRepository<Tienda, Long>{
        
    boolean existsByNombreIgnoreCase(String nombre);

    List<Tienda> findAllByOrderByNombreAsc();

}
