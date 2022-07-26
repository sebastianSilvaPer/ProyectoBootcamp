
DROP SCHEMA IF EXISTS materias_db ;
CREATE SCHEMA IF NOT EXISTS materias_db;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA materias_db;
SET search_path TO materias_db;


-- Create Tables
-- -------------------------------------------------------------------

-- Table curso
DROP TABLE IF EXISTS CURSO;

CREATE TABLE IF NOT EXISTS CURSO(
  id uuid DEFAULT uuid_generate_v4(),
  nombre VARCHAR(45) NOT NULL,
  descripcion VARCHAR(45) NULL,
  borrado BOOLEAN NOT NULL DEFAULT 'F',
  PRIMARY KEY(id));

-- Table materia
DROP TABLE IF EXISTS MATERIA;

CREATE TABLE IF NOT EXISTS MATERIA(
  id uuid DEFAULT uuid_generate_v4(),
  dia VARCHAR(10) NULL,
  hora INT NULL,
  fecha_fin DATE NULL,
  fecha_inicio DATE NULL,
  borrado BOOLEAN NOT NULL DEFAULT 'F',
  curso_id uuid NOT NULL,
  PRIMARY KEY(id),
  CONSTRAINT fk_materia_curso
    FOREIGN KEY (curso_id)
    REFERENCES CURSO (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX fk_materia_curso_idx ON MATERIA(curso_id ASC);

CREATE INDEX id_index_curso
ON CURSO(id, borrado);

CREATE INDEX id_index_materia
ON MATERIA(id, borrado);

-- Table usuario
DROP TABLE IF EXISTS USUARIO;

CREATE TABLE IF NOT EXISTS USUARIO(
  id uuid DEFAULT uuid_generate_v4(),
  nombre VARCHAR(45) NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  correo VARCHAR(45) NOT NULL,
  clave VARCHAR(70) NOT NULL,
  rol VARCHAR(45) NOT NULL,
  PRIMARY KEY(id));