package io.github.anismelow.arelu.materiaprima.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class UnidadMedidaTest {

    @Test
    void convierteKilogramosAGramos() {
        BigDecimal resultado = UnidadMedida.KILOGRAMO.aUnidadBase(new BigDecimal("5"));

        assertThat(resultado).isEqualByComparingTo("5000");
    }

    @Test
    void convierteCantidadesConDecimales() {
        BigDecimal resultado = UnidadMedida.KILOGRAMO.aUnidadBase(new BigDecimal("0.250"));

        assertThat(resultado).isEqualByComparingTo("250");
    }

    @Test
    void kilogramoEsCompatibleConPeso() {
        assertThat(UnidadMedida.KILOGRAMO.esCompatibleCon(TipoMedida.PESO)).isTrue();
    }

    @Test
    void litroNoEsCompatibleConPeso() {
        assertThat(UnidadMedida.LITRO.esCompatibleCon(TipoMedida.PESO)).isFalse();
    }
}