CREATE TABLE ingrediente (
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    tipo_medida  VARCHAR(20)  NOT NULL,

    CONSTRAINT ck_ingrediente_tipo_medida
        CHECK (tipo_medida IN ('PESO', 'VOLUMEN', 'UNIDAD'))
);

CREATE UNIQUE INDEX uq_ingrediente_nombre ON ingrediente (LOWER(nombre));

CREATE TABLE tienda (
    id      BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL
);

CREATE UNIQUE INDEX uq_tienda_nombre ON tienda (LOWER(nombre));


CREATE TABLE compra (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ingrediente_id  BIGINT        NOT NULL,
    tienda_id       BIGINT,                              
    fecha           DATE          NOT NULL,
    cantidad        NUMERIC(10,3) NOT NULL,
    unidad          VARCHAR(20)   NOT NULL,
    precio_total    NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_compra_ingrediente
        FOREIGN KEY (ingrediente_id) REFERENCES ingrediente (id),
    CONSTRAINT fk_compra_tienda
        FOREIGN KEY (tienda_id) REFERENCES tienda (id),
    CONSTRAINT ck_compra_unidad
        CHECK (unidad IN ('KILOGRAMO', 'GRAMO', 'LITRO', 'MILILITRO', 'UNIDAD')),
    CONSTRAINT ck_compra_cantidad_positiva
        CHECK (cantidad > 0),
    CONSTRAINT ck_compra_precio_total_positivo
        CHECK (precio_total > 0)
);

CREATE INDEX ix_compra_ingrediente_fecha ON compra (ingrediente_id, fecha);

CREATE INDEX ix_compra_tienda ON compra (tienda_id);