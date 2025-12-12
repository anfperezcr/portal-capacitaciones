-- 001_schema.sql
CREATE TABLE IF NOT EXISTS usuarios (
  id SERIAL PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  nombre VARCHAR(255) NOT NULL,
  rol VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS cursos (
  id SERIAL PRIMARY KEY,
  nombre VARCHAR(255) UNIQUE NOT NULL,
  descripcion TEXT,
  modulo VARCHAR(50) NOT NULL,
  activo boolean DEFAULT true
);

CREATE TABLE IF NOT EXISTS inscripciones (
  id SERIAL PRIMARY KEY,
  fecha_inscripcion TIMESTAMP NOT NULL,
  usuario_id INTEGER NOT NULL,
  curso_id INTEGER NOT NULL,
  CONSTRAINT fk_usuario FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
  CONSTRAINT fk_curso FOREIGN KEY(curso_id) REFERENCES cursos(id),
  CONSTRAINT uq_usuario_curso UNIQUE(usuario_id, curso_id)
);

CREATE TABLE IF NOT EXISTS progresos (
  id SERIAL PRIMARY KEY,
  estado VARCHAR(50) NOT NULL,
  fecha_actualizacion TIMESTAMP NOT NULL,
  usuario_id INTEGER NOT NULL,
  curso_id INTEGER NOT NULL,
  CONSTRAINT fk_progreso_usuario FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
  CONSTRAINT fk_progreso_curso FOREIGN KEY(curso_id) REFERENCES cursos(id),
  CONSTRAINT uq_progreso_usuario_curso UNIQUE(usuario_id, curso_id)
);

CREATE TABLE IF NOT EXISTS insignias (
  id SERIAL PRIMARY KEY,
  imagen VARCHAR(255),
  fecha_otorgada TIMESTAMP,
  usuario_id INTEGER NOT NULL,
  curso_id INTEGER NOT NULL,
  CONSTRAINT fk_insignia_usuario FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
  CONSTRAINT fk_insignia_curso FOREIGN KEY(curso_id) REFERENCES cursos(id)
);
