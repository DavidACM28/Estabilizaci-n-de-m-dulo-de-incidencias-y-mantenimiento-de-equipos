INSERT INTO sedes (codigo, nombre, ciudad, direccion, activa, created_at, updated_at)
VALUES
    ('LIM-MIR', 'AndeLab Lima Miraflores', 'Lima', 'Av. Larco 410, Miraflores', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('ARE-YAN', 'AndeLab Arequipa Yanahuara', 'Arequipa', 'Calle Jerusalén 215, Yanahuara', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('TRU-CEN', 'AndeLab Trujillo Centro', 'Trujillo', 'Jr. Pizarro 550, Centro Histórico', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO equipos_laboratorio (codigo_patrimonial, nombre, marca, modelo, numero_serie, area, activo, estado, sede_id, created_at, updated_at)
VALUES
    ('EQ-LIM-001', 'Analizador Hematológico XN-550', 'Sysmex', 'XN-550', 'XN550-LM-001', 'Hematología', TRUE, 'OPERATIVO',
     (SELECT id FROM sedes WHERE codigo = 'LIM-MIR'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EQ-LIM-002', 'Analizador Bioquímico BS-240', 'Mindray', 'BS-240', 'BS240-LM-002', 'Bioquímica', TRUE, 'OPERATIVO',
     (SELECT id FROM sedes WHERE codigo = 'LIM-MIR'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EQ-ARE-001', 'Centrífuga Clínica Z326', 'Hermle', 'Z326', 'Z326-AQ-001', 'Procesamiento', TRUE, 'EN_MANTENIMIENTO',
     (SELECT id FROM sedes WHERE codigo = 'ARE-YAN'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('EQ-TRU-001', 'Microscopio de Fluorescencia CX43', 'Olympus', 'CX43', 'CX43-TR-001', 'Microbiología', TRUE, 'FUERA_SERVICIO',
     (SELECT id FROM sedes WHERE codigo = 'TRU-CEN'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO repuestos (codigo, nombre, descripcion, unidad_medida, stock_actual, stock_minimo, activo, version, created_at, updated_at)
VALUES
    ('REP-001', 'Bomba Peristáltica', 'Bomba para analizador bioquímico', 'unidad', 5, 2, TRUE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('REP-002', 'Kit de Mangueras', 'Juego de mangueras de reemplazo', 'kit', 3, 3, TRUE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('REP-003', 'Sensor Óptico', 'Sensor de lectura para equipo hematológico', 'unidad', 8, 2, TRUE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('REP-004', 'Fusible 5A', 'Fusible de protección eléctrica', 'unidad', 20, 5, TRUE, 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO app_users (email, password, nombre_completo, role, activo, sede_id, created_at, updated_at)
VALUES
    ('admin@andelab.pe', '{noop}Admin123*', 'Administrador General', 'ADMIN', TRUE, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('operaciones@andelab.pe', '{noop}Admin123*', 'Coordinación de Operaciones', 'OPERACIONES', TRUE, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('tecnico1@andelab.pe', '{noop}Admin123*', 'Técnico Biomédico Principal', 'TECNICO', TRUE, (SELECT id FROM sedes WHERE codigo = 'LIM-MIR'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('sede.lima@andelab.pe', '{noop}Admin123*', 'Responsable Sede Lima', 'SEDE', TRUE, (SELECT id FROM sedes WHERE codigo = 'LIM-MIR'), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
