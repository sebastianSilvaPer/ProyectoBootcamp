package com.proyecto.bootcamp.Services.Mapper.materia;

import java.util.UUID;

import org.mapstruct.Mapper;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

@Mapper(componentModel = "spring")
public interface MateriaMapper {
    MateriaDTO materiaToDTO(Materia materia);
    Materia dtoToMateria(MateriaDTO materiaDTO, UUID curso);
}

