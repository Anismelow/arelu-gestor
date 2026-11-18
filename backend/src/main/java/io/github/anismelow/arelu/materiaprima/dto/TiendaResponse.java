package io.github.anismelow.arelu.materiaprima.dto;

import io.github.anismelow.arelu.materiaprima.model.Tienda;

public record TiendaResponse(Long id, String nombre) {

    public static TiendaResponse from(Tienda tienda){
        return new TiendaResponse(tienda.getId(), tienda.getNombre());
    }

}
