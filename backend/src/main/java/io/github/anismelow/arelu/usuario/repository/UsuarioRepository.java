package io.github.anismelow.arelu.usuario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.anismelow.arelu.usuario.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where lower(u.nombreDeUsuario) = lower(:nombreDeUsuario)")
    Optional<Usuario> buscarPorNombre(@Param("nombreDeUsuario") String nombreDeUsuario);

    public List<Usuario> findAllByOrderByNombreDeUsuarioAsc();
}
