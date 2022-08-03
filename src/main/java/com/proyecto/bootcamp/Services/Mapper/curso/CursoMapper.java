package com.proyecto.bootcamp.Services.Mapper.curso;

import org.mapstruct.Mapper;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import com.proyecto.bootcamp.Services.Mapper.MapperGeneric;

@Mapper(componentModel = "spring")
public interface CursoMapper extends MapperGeneric<Curso, CursoDTO> {
    
}
