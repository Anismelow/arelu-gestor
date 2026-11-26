package io.github.anismelow.arelu.auth.dto;

public record TokenResponse(
        String token,
        String tipo,
        long expiraEnSegundos) {

}
