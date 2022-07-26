package com.proyecto.bootcamp.Services.Mapper;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTOWithCurso;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T03:20:22-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
public class MateriaMapperImpl implements MateriaMapper {

    @Override
    public MateriaDTO materiaToDTO(Materia materia) {
        if ( materia == null ) {
            return null;
        }

        MateriaDTO materiaDTO = new MateriaDTO();

        materiaDTO.setId( materia.getId() );
        materiaDTO.setDia( materia.getDia() );
        materiaDTO.setHora( materia.getHora() );
        materiaDTO.setFechafin( materia.getFechafin() );
        materiaDTO.setFechainicio( materia.getFechainicio() );

        return materiaDTO;
    }

    @Override
    public MateriaDTOWithCurso materiaToDTOWithCurso(Materia materia) {
        if ( materia == null ) {
            return null;
        }

        MateriaDTOWithCurso materiaDTOWithCurso = new MateriaDTOWithCurso();

        materiaDTOWithCurso.setId( materia.getId() );
        materiaDTOWithCurso.setDia( materia.getDia() );
        materiaDTOWithCurso.setHora( materia.getHora() );
        materiaDTOWithCurso.setFechafin( materia.getFechafin() );
        materiaDTOWithCurso.setFechainicio( materia.getFechainicio() );
        materiaDTOWithCurso.setCurso( materia.getCurso() );

        return materiaDTOWithCurso;
    }

    @Override
    public Materia dtoToMateria(MateriaDTO materiaDTO, UUID curso) {
        if ( materiaDTO == null && curso == null ) {
            return null;
        }

        Materia materia = new Materia();

        if ( materiaDTO != null ) {
            materia.setId( materiaDTO.getId() );
            materia.setDia( materiaDTO.getDia() );
            materia.setHora( materiaDTO.getHora() );
            materia.setFechafin( materiaDTO.getFechafin() );
            materia.setFechainicio( materiaDTO.getFechainicio() );
        }
        materia.setCurso( curso );

        return materia;
    }

    @Override
    public MateriaDTOWithCurso dtoToWithCurso(MateriaDTO materiaDTO, UUID curso) {
        if ( materiaDTO == null && curso == null ) {
            return null;
        }

        MateriaDTOWithCurso materiaDTOWithCurso = new MateriaDTOWithCurso();

        if ( materiaDTO != null ) {
            materiaDTOWithCurso.setId( materiaDTO.getId() );
            materiaDTOWithCurso.setDia( materiaDTO.getDia() );
            materiaDTOWithCurso.setHora( materiaDTO.getHora() );
            materiaDTOWithCurso.setFechafin( materiaDTO.getFechafin() );
            materiaDTOWithCurso.setFechainicio( materiaDTO.getFechainicio() );
        }
        materiaDTOWithCurso.setCurso( curso );

        return materiaDTOWithCurso;
    }

    @Override
    public Materia dtoWithCursoToMateria(MateriaDTOWithCurso materiaDTOWithCurso) {
        if ( materiaDTOWithCurso == null ) {
            return null;
        }

        Materia materia = new Materia();

        materia.setCurso( materiaDTOWithCurso.getCurso() );
        materia.setId( materiaDTOWithCurso.getId() );
        materia.setDia( materiaDTOWithCurso.getDia() );
        materia.setHora( materiaDTOWithCurso.getHora() );
        materia.setFechafin( materiaDTOWithCurso.getFechafin() );
        materia.setFechainicio( materiaDTOWithCurso.getFechainicio() );

        return materia;
    }

    @Override
    public MateriaDTO dtoWithCursoToMateriaDTO(MateriaDTOWithCurso materiaDTOWithCurso) {
        if ( materiaDTOWithCurso == null ) {
            return null;
        }

        MateriaDTO materiaDTO = new MateriaDTO();

        materiaDTO.setId( materiaDTOWithCurso.getId() );
        materiaDTO.setDia( materiaDTOWithCurso.getDia() );
        materiaDTO.setHora( materiaDTOWithCurso.getHora() );
        materiaDTO.setFechafin( materiaDTOWithCurso.getFechafin() );
        materiaDTO.setFechainicio( materiaDTOWithCurso.getFechainicio() );

        return materiaDTO;
    }
}
