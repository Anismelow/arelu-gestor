package io.github.anismelow.arelu.materiaprima.dto;

import io.github.anismelow.arelu.materiaprima.model.TipoMedida;
import io.github.anismelow.arelu.materiaprima.model.Ingrediente;
public record IngredienteResponse(Long id, String nombre, TipoMedida tipoMedida) {

    public static IngredienteResponse from (Ingrediente ingrediente){
        return new IngredienteResponse(
            ingrediente.getId(),
            ingrediente.getNombre(),
            ingrediente.getTipoMedida()
        );
    }
}
