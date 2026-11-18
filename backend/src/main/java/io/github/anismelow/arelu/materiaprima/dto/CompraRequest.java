package io.github.anismelow.arelu.materiaprima.dto;

import io.github.anismelow.arelu.materiaprima.model.UnidadMedida;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraRequest(
      @NotNull(message = "El ingrediente es obligatorio")
        Long ingredienteId,

        Long tiendaId,   // opcional

        @NotNull(message = "La fecha es obligatoria")
        @PastOrPresent(message = "La fecha no puede ser futura")
        LocalDate fecha,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor que 0")
        @Digits(integer = 7, fraction = 3, message = "La cantidad admite hasta 7 enteros y 3 decimales")
        BigDecimal cantidad,

        @NotNull(message = "La unidad es obligatoria")
        UnidadMedida unidad,

        @NotNull(message = "El precio total es obligatorio")
        @Positive(message = "El precio total debe ser mayor que 0")
        @Digits(integer = 8, fraction = 2, message = "El precio admite hasta 8 enteros y 2 decimales")
        BigDecimal precioTotal
) {


}
