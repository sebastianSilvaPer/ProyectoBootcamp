package com.proyecto.bootcamp.Services.Mapper;

import java.util.UUID;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTOWithCurso;

// @Mapper
@Mapper
public interface MateriaMapper {
    MateriaMapper INSTANCE = Mappers.getMapper(MateriaMapper.class);

    //Map materia entity to
    public MateriaDTO materiaToDTO(Materia materia);
    public MateriaDTOWithCurso materiaToDTOWithCurso(Materia materia);
    
    //Map materiaDTO to
    public Materia dtoToMateria(MateriaDTO materiaDTO, UUID curso);
    public MateriaDTOWithCurso dtoToWithCurso(MateriaDTO materiaDTO, UUID curso);

    //Map materiaDTOWithCurso to
    public Materia dtoWithCursoToMateria(MateriaDTOWithCurso materiaDTOWithCurso);
    public MateriaDTO dtoWithCursoToMateriaDTO(MateriaDTOWithCurso materiaDTOWithCurso);
}
