SET search_path TO materias_db;
insert into CURSO(nombre, descripcion) values 
('Calculo diferencial','Lorem ipsum dolor sit amet, consectetur.'),
('Calculo integral','Lorem ipsum dolor sit amet, consectetur.'),
('Calculo vectorial','Lorem ipsum dolor sit amet, consectetur.'),
('Fisica mecanica','Lorem ipsum dolor sit amet, consectetur.'),
('Fisica electromagnetismo','Lorem ipsum dolor sit amet, consectetur.'),
('programacion funcional','Lorem ipsum dolor sit amet, consectetur.');


INSERT INTO MATERIA(dia, hora, fecha_fin, fecha_inicio, curso_id) VALUES
('Martes', 8, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo diferencial')),
('Miercoles', 14, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo diferencial')),
('Lunes', 8, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo diferencial')),
('Martes', 11, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo integral')),
('Jueves', 8, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo integral')),
('Miercoles', 8, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Fisica mecanica')),
('Martes', 16, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Fisica mecanica')),
('Viernes', 11, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='programacion funcional')),
('Miercoles', 9, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo vectorial')),
('Lunes', 15, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Calculo vectorial')),
('Viernes', 9, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Fisica electromagnetismo')),
('Martes', 7, '2022-06-01', '2022-11-023', (select id from CURSO where nombre='Fisica electromagnetismo'));

INSERT INTO USUARIO(nombre, apellido, correo, clave, rol) VALUES 
('Carlos', 'Silva', 'carlos@gmail.com', '$2a$12$5JpcLwsGEMqmLJphFMi9G.inBJCIiZBPS99n0nIYXmf4nVLWUPi/S', 'ROL_ESTUDIANTE'),
('Sebastian', 'Silva', 'sebas@gmail.com', '$2a$12$i1ShK3WbooyrIn2MiJx.heYw4YE59bJ1NCY5OIKFk5FgDmxsWaPze', 'ROL_ESTUDIANTE'),
('Pedro', 'Picapiedra', 'pedrop@gmail.com', '$2a$12$IvJl4f11rHpbw.xkoCINhO0gsidOVVtF6ZRe5Z.4Pkto/ZTgDVUz2', 'ROL_ADMIN'),
('Flor', 'Dias', 'flor@gmail.com', '$2a$12$gZs1TkfSHhFNO3BEjqsyAO3s6BHTerPGRfLITeJVwAoFlFaEVOyKi', 'ROL_PROFESOR'),
('Marta', 'Ramirez', 'martar@gmail.com', '$2a$12$nGPYu4r4TkssrORuX4bpUeFI54fPSYs19pLN9zQY4lNxa5S6rUaJa', 'ROL_PROFESOR');