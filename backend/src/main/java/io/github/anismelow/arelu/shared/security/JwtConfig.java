package io.github.anismelow.arelu.shared.security;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import lombok.RequiredArgsConstructor;
@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final JwtProperties jwtProperties;

    @Bean
    public JwtEncoder jwtEncoder() {
        return NimbusJwtEncoder.withSecretKey(claveSecreta()).build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(claveSecreta())
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    private SecretKey claveSecreta() {
        byte[] bytes = Base64.getDecoder().decode(jwtProperties.secreto());            // texto Base64 → bytes
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

}
