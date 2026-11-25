package io.github.anismelow.arelu.usuario.model;

import java.time.Instant;

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
@Table(name = "usuario")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_de_usuario", nullable = false, length = 60)
    private String nombreDeUsuario;

    @Column(name = "contrasena_hash", nullable = false, length = 60)
    private String contrasenaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @Column(name = "esta_activo", nullable = false)
    private boolean activo;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private Instant creadoEn;

    public Usuario(String nombreDeUsuario, String contrasenaHash, Rol rol) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
        this.activo = true;
        this.creadoEn = Instant.now();
    }

    public void desactivar() {
        this.activo = false;
    }
}
