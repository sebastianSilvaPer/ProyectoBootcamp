package com.proyecto.bootcamp.Services.Mapper;

import org.mapstruct.Mapper;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;

@Mapper(componentModel = "spring")
public interface CursoMapper {
    public CursoDTO cursoToDTO(Curso curso);
    public Curso dtoToCurso(CursoDTO cursoDTO);
}
