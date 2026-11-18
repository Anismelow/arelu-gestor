package io.github.anismelow.arelu.materiaprima.dto;

import io.github.anismelow.arelu.materiaprima.model.UnidadMedida;
import io.github.anismelow.arelu.materiaprima.model.Compra;
import io.github.anismelow.arelu.materiaprima.model.Tienda;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CompraResponse(
      Long id,
        Long ingredienteId,
        String ingrediente,
        Long tiendaId,
        String tienda,
        LocalDate fecha,
        BigDecimal cantidad,
        UnidadMedida unidad,
        BigDecimal precioTotal,
        BigDecimal precioPorUnidadBase
) {

        public static CompraResponse from(Compra compra) {
        Tienda tienda = compra.getTienda();
        return new CompraResponse(
                compra.getId(),
                compra.getIngrediente().getId(),
                compra.getIngrediente().getNombre(),
                tienda == null ? null : tienda.getId(),
                tienda == null ? null : tienda.getNombre(),
                compra.getFecha(),
                compra.getCantidad(),
                compra.getUnidad(),
                compra.getPrecioTotal(),
                compra.precioPorUnidadBase());
    }
}
