package io.github.anismelow.arelu.materiaprima.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "ingrediente")
@Getter 
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Ingrediente {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private String nombre;

    @Enumerated (EnumType.STRING)
    @Column (name = "tipo_medida", nullable = false,length = 20)
    private TipoMedida tipoMedida;


    
    public Ingrediente(String nombre, TipoMedida tipoMedida){
        this.nombre = nombre;
        this.tipoMedida = tipoMedida;
    }

}
