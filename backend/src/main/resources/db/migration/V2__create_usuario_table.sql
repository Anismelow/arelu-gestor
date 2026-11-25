CREATE TABLE usuario (
    id                 BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre_de_usuario  VARCHAR(60) NOT NULL,
    contrasena_hash    VARCHAR(60) NOT NULL,
    rol                VARCHAR(20) NOT NULL,
    esta_activo        BOOLEAN     NOT NULL DEFAULT true,
    creado_en          TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT ck_usuario_rol
        CHECK (rol IN ('ADMIN', 'SUB_ADMIN'))
);

CREATE UNIQUE INDEX uq_usuario_nombre_de_usuario ON usuario (LOWER(nombre_de_usuario));