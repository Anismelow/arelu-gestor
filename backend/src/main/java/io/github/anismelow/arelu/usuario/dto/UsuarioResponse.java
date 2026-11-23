package io.github.anismelow.arelu.usuario.dto;

import java.time.Instant;

import io.github.anismelow.arelu.usuario.model.Rol;
import io.github.anismelow.arelu.usuario.model.Usuario;

public record UsuarioResponse(Long id, String nombreDeUsuario, Rol rol, boolean activo, Instant creadoEn) {

    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombreDeUsuario(),
                usuario.getRol(),
                usuario.isActivo(),
                usuario.getCreadoEn());
    }
}
