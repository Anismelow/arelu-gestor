package io.github.anismelow.arelu.materiaprima.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import io.github.anismelow.arelu.materiaprima.model.UnidadMedida;
import io.github.anismelow.arelu.shared.exception.ReglaNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compra")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Compra {

    /** Decimales del precio por g, ml o ud (4,80 € / 5000 g = 0,000960 €/g). */
    private static final int DECIMALES_PRECIO_BASE = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingrediente_id", nullable = false)
    private Ingrediente ingrediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tienda_id")
    private Tienda tienda;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal cantidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UnidadMedida unidad;

    @Column(name = "precio_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioTotal;

    public Compra(Ingrediente ingrediente, Tienda tienda, LocalDate fecha,
                  BigDecimal cantidad, UnidadMedida unidad, BigDecimal precioTotal) {
        if (!unidad.esCompatibleCon(ingrediente.getTipoMedida())) {
            throw new ReglaNegocioException("La unidad " + unidad
                    + " no sirve para un ingrediente que se mide por " + ingrediente.getTipoMedida());
        }
        this.ingrediente = ingrediente;
        this.tienda = tienda;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.precioTotal = precioTotal;
    }

    /** Precio de 1 g, 1 ml o 1 ud. Es un dato calculado: no se guarda en la BD. */
    public BigDecimal precioPorUnidadBase() {
        return precioTotal.divide(unidad.aUnidadBase(cantidad), DECIMALES_PRECIO_BASE, RoundingMode.HALF_UP);
    }
}