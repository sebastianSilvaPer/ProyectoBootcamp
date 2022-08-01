package com.proyecto.bootcamp.Services.Mapper;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-01T12:54:50-0500",
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

        materiaDTO.setId( materia.getId() );
        materiaDTO.setDia( materia.getDia() );
        materiaDTO.setHora( materia.getHora() );
        materiaDTO.setFechafin( materia.getFechafin() );
        materiaDTO.setFechainicio( materia.getFechainicio() );

        return materiaDTO;
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
}
