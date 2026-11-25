package io.github.anismelow.arelu.usuario.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@ConfigurationProperties(prefix = "app.admin")
@Validated 
public record AdminProperties(
      @NotBlank  String nombreDeUsuario,
      @NotBlank  String contrasena) {

}
