package com.proyecto.bootcamp.Services.Mapper;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTOWithCurso;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-28T09:26:13-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class MateriaMapperImpl implements MateriaMapper {

    @Override
    public MateriaDTO materiaToDTO(Materia materia) {
        if ( materia == null ) {
            return null;
        }

        MateriaDTO materiaDTO = new MateriaDTO();

        materiaDTO.setDia( materia.getDia() );
        materiaDTO.setFechafin( materia.getFechafin() );
        materiaDTO.setFechainicio( materia.getFechainicio() );
        materiaDTO.setHora( materia.getHora() );
        materiaDTO.setId( materia.getId() );

        return materiaDTO;
    }

    @Override
    public MateriaDTOWithCurso materiaToDTOWithCurso(Materia materia) {
        if ( materia == null ) {
            return null;
        }

        MateriaDTOWithCurso materiaDTOWithCurso = new MateriaDTOWithCurso();

        materiaDTOWithCurso.setDia( materia.getDia() );
        materiaDTOWithCurso.setFechafin( materia.getFechafin() );
        materiaDTOWithCurso.setFechainicio( materia.getFechainicio() );
        materiaDTOWithCurso.setHora( materia.getHora() );
        materiaDTOWithCurso.setId( materia.getId() );
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
            materia.setDia( materiaDTO.getDia() );
            materia.setFechafin( materiaDTO.getFechafin() );
            materia.setFechainicio( materiaDTO.getFechainicio() );
            materia.setHora( materiaDTO.getHora() );
            materia.setId( materiaDTO.getId() );
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
            materiaDTOWithCurso.setDia( materiaDTO.getDia() );
            materiaDTOWithCurso.setFechafin( materiaDTO.getFechafin() );
            materiaDTOWithCurso.setFechainicio( materiaDTO.getFechainicio() );
            materiaDTOWithCurso.setHora( materiaDTO.getHora() );
            materiaDTOWithCurso.setId( materiaDTO.getId() );
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
        materia.setDia( materiaDTOWithCurso.getDia() );
        materia.setFechafin( materiaDTOWithCurso.getFechafin() );
        materia.setFechainicio( materiaDTOWithCurso.getFechainicio() );
        materia.setHora( materiaDTOWithCurso.getHora() );
        materia.setId( materiaDTOWithCurso.getId() );

        return materia;
    }

    @Override
    public MateriaDTO dtoWithCursoToMateriaDTO(MateriaDTOWithCurso materiaDTOWithCurso) {
        if ( materiaDTOWithCurso == null ) {
            return null;
        }

        MateriaDTO materiaDTO = new MateriaDTO();

        materiaDTO.setDia( materiaDTOWithCurso.getDia() );
        materiaDTO.setFechafin( materiaDTOWithCurso.getFechafin() );
        materiaDTO.setFechainicio( materiaDTOWithCurso.getFechainicio() );
        materiaDTO.setHora( materiaDTOWithCurso.getHora() );
        materiaDTO.setId( materiaDTOWithCurso.getId() );

        return materiaDTO;
    }
}
