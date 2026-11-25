package io.github.anismelow.arelu.usuario.dto;

import io.github.anismelow.arelu.usuario.model.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearUsuarioRequest(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 60, message = "El nombre de usuario no puede superar los {max} caracteres")
        String nombreDeUsuario,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 10, max = 72, message = "La contraseña debe tener entre {min} y {max} caracteres")
        String contrasena,

        @NotNull(message = "El rol es obligatorio")
        Rol rol
) {
}