package io.github.anismelow.arelu.auth.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.anismelow.arelu.auth.dto.LoginRequest;
import io.github.anismelow.arelu.auth.dto.TokenResponse;
import io.github.anismelow.arelu.auth.service.TokenService;
import io.github.anismelow.arelu.shared.security.JwtProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication solicitud = UsernamePasswordAuthenticationToken.unauthenticated(request.nombreDeUsuario(),
                request.contrasena());

        Authentication resultado = authenticationManager.authenticate(solicitud);

        String token = tokenService.generarToken(resultado);

        return new TokenResponse(
                token,
                "Bearer",
                jwtProperties.duracion().toSeconds());

    }

}
