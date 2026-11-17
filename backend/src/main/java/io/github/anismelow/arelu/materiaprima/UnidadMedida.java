package io.github.anismelow.arelu.materiaprima;
import java.math.BigDecimal;


public enum UnidadMedida {

     KILOGRAMO(TipoMedida.PESO, new BigDecimal("1000")),
    GRAMO(TipoMedida.PESO, BigDecimal.ONE),
    LITRO(TipoMedida.VOLUMEN, new BigDecimal("1000")),
    MILILITRO(TipoMedida.VOLUMEN, BigDecimal.ONE),
    UNIDAD(TipoMedida.UNIDAD, BigDecimal.ONE);
    
    private final TipoMedida tipo;
    private final BigDecimal factorABase;

     UnidadMedida(TipoMedida tipo, BigDecimal factorABase) {
        this.tipo = tipo;
        this.factorABase = factorABase;
    }

    public TipoMedida getTipo() {
        return tipo;
    }

    /** Convierte una cantidad de esta unidad a la unidad base. Ejemplo: 5 KILOGRAMO → 5000 (g). */
    public BigDecimal aUnidadBase(BigDecimal cantidad) {
        return cantidad.multiply(factorABase);
    }

    /** Indica si esta unidad se puede usar con un ingrediente del tipo indicado. */
    public boolean esCompatibleCon(TipoMedida tipoIngrediente) {
        return this.tipo == tipoIngrediente;
    }

}
