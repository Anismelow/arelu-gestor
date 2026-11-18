package io.github.anismelow.arelu.materiaprima.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;


import io.github.anismelow.arelu.shared.exception.ReglaNegocioException;

class CompraTest {

    private final Ingrediente harina = new Ingrediente("Harina", TipoMedida.PESO);

    @Test
    void calculaElPrecioPorGramo() {
        Compra saco = new Compra(harina, null, LocalDate.now(),
                new BigDecimal("5"), UnidadMedida.KILOGRAMO, new BigDecimal("4.80"));

        assertThat(saco.precioPorUnidadBase()).isEqualByComparingTo("0.00096");
    }

    @Test
    void elSacoSaleMasBaratoQueLaBolsa() {
        Compra bolsa = new Compra(harina, null, LocalDate.now(),
                new BigDecimal("1"), UnidadMedida.KILOGRAMO, new BigDecimal("1.10"));
        Compra saco = new Compra(harina, null, LocalDate.now(),
                new BigDecimal("5"), UnidadMedida.KILOGRAMO, new BigDecimal("4.80"));

        assertThat(saco.precioPorUnidadBase()).isLessThan(bolsa.precioPorUnidadBase());
    }

    @Test
    void noPermiteComprarUnIngredienteDePesoEnLitros() {
        assertThatThrownBy(() -> new Compra(harina, null, LocalDate.now(),
                new BigDecimal("2"), UnidadMedida.LITRO, new BigDecimal("3.00")))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("LITRO");
    }
}