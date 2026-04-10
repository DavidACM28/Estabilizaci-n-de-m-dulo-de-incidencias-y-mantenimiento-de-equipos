CREATE TABLE sedes (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    ciudad VARCHAR(80) NOT NULL,
    direccion VARCHAR(250) NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE equipos_laboratorio (
    id BIGSERIAL PRIMARY KEY,
    codigo_patrimonial VARCHAR(40) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    marca VARCHAR(80),
    modelo VARCHAR(80),
    numero_serie VARCHAR(80),
    area VARCHAR(120),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    estado VARCHAR(30) NOT NULL,
    sede_id BIGINT NOT NULL REFERENCES sedes(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE app_users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(150) NOT NULL,
    role VARCHAR(30) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    sede_id BIGINT REFERENCES sedes(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE incidencias (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE,
    titulo VARCHAR(160) NOT NULL,
    descripcion VARCHAR(1000) NOT NULL,
    tipo VARCHAR(40) NOT NULL,
    prioridad VARCHAR(30) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    fecha_reporte TIMESTAMP NOT NULL,
    fecha_limite_atencion TIMESTAMP NOT NULL,
    fecha_revision TIMESTAMP,
    fecha_resolucion TIMESTAMP,
    fecha_cierre TIMESTAMP,
    comentario_revision VARCHAR(500),
    sede_id BIGINT NOT NULL REFERENCES sedes(id),
    equipo_id BIGINT NOT NULL REFERENCES equipos_laboratorio(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE repuestos (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    unidad_medida VARCHAR(30) NOT NULL,
    stock_actual INTEGER NOT NULL,
    stock_minimo INTEGER NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE ordenes_trabajo (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(40) NOT NULL UNIQUE,
    incidencia_id BIGINT NOT NULL UNIQUE REFERENCES incidencias(id),
    estado VARCHAR(30) NOT NULL,
    descripcion_trabajo VARCHAR(1000) NOT NULL,
    diagnostico_inicial VARCHAR(1000),
    observacion_cierre VARCHAR(1000),
    fecha_asignacion TIMESTAMP,
    fecha_inicio TIMESTAMP,
    fecha_fin TIMESTAMP,
    tecnico_asignado_id BIGINT REFERENCES app_users(id),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE orden_trabajo_repuestos (
    id BIGSERIAL PRIMARY KEY,
    orden_trabajo_id BIGINT NOT NULL REFERENCES ordenes_trabajo(id) ON DELETE CASCADE,
    repuesto_id BIGINT NOT NULL REFERENCES repuestos(id),
    cantidad INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uk_orden_repuesto UNIQUE (orden_trabajo_id, repuesto_id)
);

CREATE INDEX idx_equipos_sede ON equipos_laboratorio (sede_id);
CREATE INDEX idx_incidencias_sede ON incidencias (sede_id);
CREATE INDEX idx_incidencias_equipo_tipo_estado ON incidencias (equipo_id, tipo, estado);
CREATE INDEX idx_ordenes_sede_estado ON ordenes_trabajo (estado, incidencia_id);
CREATE INDEX idx_repuestos_stock ON repuestos (stock_actual, stock_minimo);
