package io.github.anismelow.arelu.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.anismelow.arelu.usuario.model.Usuario;

public interface UsuarioRepository  extends  JpaRepository<Usuario, Long>{
}
