package io.github.anismelow.arelu.usuario.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    private final Usuario usuario = new Usuario("anyell", "hash-falso", Rol.ADMIN);

    @Test
    void unUsuarioNuevoEstaActivo() {
        assertThat(usuario.isActivo()).isTrue();
    }

    @Test
    void alDesactivarloDejaDeEstarActivo() {
        usuario.desactivar();

        assertThat(usuario.isActivo()).isFalse();
    }

    @Test
    void alCrearSePoneLaFechaDeCreacion() {
        assertThat(usuario.getCreadoEn()).isNotNull();
    }

}
