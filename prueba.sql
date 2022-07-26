
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




-- -----------------------------------------------------
-- Table `mydb`.`Usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Usuario (
  id uuid DEFAULT uuid_generate_v4(),
  nombre VARCHAR(45) NULL,
  apellido VARCHAR(45) NULL,
  correo VARCHAR(45) NULL,
  clave VARCHAR(45) NULL,
  PRIMARY KEY (id));


-- -----------------------------------------------------
-- Table `mydb`.`Profesor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS Profesor (
  id uuid DEFAULT uuid_generate_v4(),
  especialidad VARCHAR(45) NULL,
  usuario_id uuid NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_profesor_usuario
    FOREIGN KEY (usuario_id)
    REFERENCES Usuario (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE INDEX `fk_Profesor_Usuario1_idx` ON `mydb`.`Profesor` (`Usuario_idUsuario` ASC) VISIBLE;



-- -----------------------------------------------------
-- Table `mydb`.`Curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Curso` (
  `idCurso` INT NOT NULL,
  `Nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(45) NULL,
  `Profesor_idProfesor` INT NOT NULL,
  PRIMARY KEY (`idCurso`),
  CONSTRAINT `fk_Curso_Profesor1`
    FOREIGN KEY (`Profesor_idProfesor`)
    REFERENCES `mydb`.`Profesor` (`idProfesor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Curso_Profesor1_idx` ON `mydb`.`Curso` (`Profesor_idProfesor` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`Materia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Materia` (
  `idMateria` INT NOT NULL,
  `Dia` VARCHAR(10) NULL,
  `Hora` INT NULL,
  `fechaFin` DATETIME NULL,
  `fechaInicio` DATETIME NULL,
  `Curso_idCurso` INT NOT NULL,
  PRIMARY KEY (`idMateria`),
  CONSTRAINT `fk_Materia_Curso`
    FOREIGN KEY (`Curso_idCurso`)
    REFERENCES `mydb`.`Curso` (`idCurso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Estudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Estudiante` (
  `idEstudiante` INT NOT NULL,
  `Carrera` VARCHAR(45) NULL,
  `Semestre` INT NULL,
  `Usuario_idUsuario` INT NOT NULL,
  PRIMARY KEY (`idEstudiante`),
  CONSTRAINT `fk_Student_Usuario1`
    FOREIGN KEY (`Usuario_idUsuario`)
    REFERENCES `mydb`.`Usuario` (`idUsuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Student_Usuario1_idx` ON `mydb`.`Estudiante` (`Usuario_idUsuario` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `mydb`.`MateriaEstudiante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`MateriaEstudiante` (
  `Materia_idMateria` INT NOT NULL,
  `Nota` DECIMAL(10) NULL,
  `MateriaEstudiantecol` VARCHAR(45) NULL,
  `Estudiante_idEstudiante` INT NOT NULL,
  PRIMARY KEY (`Materia_idMateria`, `Estudiante_idEstudiante`),
  CONSTRAINT `fk_MateriaStudent_Materia1`
    FOREIGN KEY (`Materia_idMateria`)
    REFERENCES `mydb`.`Materia` (`idMateria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_MateriaEstudiante_Estudiante1`
    FOREIGN KEY (`Estudiante_idEstudiante`)
    REFERENCES `mydb`.`Estudiante` (`idEstudiante`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_MateriaStudent_Materia1_idx` ON `mydb`.`MateriaEstudiante` (`Materia_idMateria` ASC) VISIBLE;

CREATE INDEX `fk_MateriaEstudiante_Estudiante1_idx` ON `mydb`.`MateriaEstudiante` (`Estudiante_idEstudiante` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
