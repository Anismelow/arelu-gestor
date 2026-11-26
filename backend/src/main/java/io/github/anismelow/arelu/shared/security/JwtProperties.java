package io.github.anismelow.arelu.shared.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.jwt")
@Validated
public record JwtProperties(
    @NotBlank  @Size (min=44, message="El secreto JWT debe tener al menos {min} caracteres")String  secreto,  @NotNull Duration duracion 
) {

}
