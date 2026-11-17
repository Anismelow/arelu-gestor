package io.github.anismelow.arelu.materiaprima;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TiendaRepository extends JpaRepository<Tienda, Long>{
        
    boolean existsByNombreIgnoreCase(String nombre);

    List<Tienda> findAllByOrderByNombreAsc();

}
