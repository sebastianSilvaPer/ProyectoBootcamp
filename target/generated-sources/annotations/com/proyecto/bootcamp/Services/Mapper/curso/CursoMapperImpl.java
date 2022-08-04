package com.proyecto.bootcamp.Services.Mapper.curso;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T21:39:23-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class CursoMapperImpl implements CursoMapper {

    @Override
    public Curso mapToEntity(CursoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setId( dto.getId() );
        curso.setNombre( dto.getNombre() );
        curso.setDescripcion( dto.getDescripcion() );

        return curso;
    }

    @Override
    public CursoDTO mapToDto(Curso entity) {
        if ( entity == null ) {
            return null;
        }

        CursoDTO cursoDTO = new CursoDTO();

        cursoDTO.setId( entity.getId() );
        cursoDTO.setNombre( entity.getNombre() );
        cursoDTO.setDescripcion( entity.getDescripcion() );

        return cursoDTO;
    }

    @Override
    public List<Curso> mapListToEntity(List<CursoDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Curso> list = new ArrayList<Curso>( dtos.size() );
        for ( CursoDTO cursoDTO : dtos ) {
            list.add( mapToEntity( cursoDTO ) );
        }

        return list;
    }

    @Override
    public List<CursoDTO> mapListToDto(List<Curso> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CursoDTO> list = new ArrayList<CursoDTO>( entities.size() );
        for ( Curso curso : entities ) {
            list.add( mapToDto( curso ) );
        }

        return list;
    }
}
