package io.github.anismelow.arelu.shared.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordConfigTest {

    private final PasswordEncoder encoder = new PasswordConfig().passwordEncoder();

    @Test
    void elHashDeBcryptTiene60Caracteres() {
        String hash = encoder.encode("tarta123");

        assertThat(hash).hasSize(60);
    }

    @Test
    void reconoceLaContrasenaCorrectaYRechazaLaIncorrecta() {
        String hash = encoder.encode("Tarta123");

        assertThat(encoder.matches("Tarta123", hash)).isTrue();
        assertThat(encoder.matches("Bizcocho9999", hash)).isFalse();
    }

    @Test
    void laMismaContrasenaDaHashesDistintos() {
        String hash1 = encoder.encode("Tarta123");
        String hash2 = encoder.encode("Tarta123");

        assertThat(hash1).isNotEqualTo(hash2);

        assertThat(encoder.matches("Tarta123", hash1)).isTrue();
        assertThat(encoder.matches("Tarta123", hash2)).isTrue();

    }

}
