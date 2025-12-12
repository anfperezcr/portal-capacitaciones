-- 002_data.sql
INSERT INTO usuarios (username, password, nombre, rol) VALUES
('admin', 'admin', 'Administrador', 'ADMIN'),
('andres', '123456', 'Andrés Pérez', 'USER')
ON CONFLICT (username) DO NOTHING;

INSERT INTO cursos (nombre, descripcion, modulo, activo) VALUES
('Curso de APIs con Spring Boot','APIs REST con Spring Boot','APIS', true),
('Fundamentos de AWS','Intro a AWS','CLOUD', true)
ON CONFLICT (nombre) DO NOTHING;
