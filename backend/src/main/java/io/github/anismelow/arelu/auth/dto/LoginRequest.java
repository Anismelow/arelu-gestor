package io.github.anismelow.arelu.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio") @Size(max = 60, message = "El nombre de usuario no puede superar los {max} caracteres") String nombreDeUsuario,

        @NotBlank(message = "La contraseña es obligatoria") @Size( max = 72, message = "La contraseña no debe de superar los {max} caracteres") String contrasena) {

    @Override
    public String toString() {
        return "LoginRequest[nombreDeUsuario=" + nombreDeUsuario + ", contrasena=****]";

    }

}
