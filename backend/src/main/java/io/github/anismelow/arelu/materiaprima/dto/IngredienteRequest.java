package io.github.anismelow.arelu.materiaprima.dto;

import io.github.anismelow.arelu.materiaprima.model.TipoMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IngredienteRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El nombre no puede superar los 100 caracteres") String nombre,

        @NotNull(message = "El tipo de medida es obligatorio") TipoMedida tipoMedida

) {

}
