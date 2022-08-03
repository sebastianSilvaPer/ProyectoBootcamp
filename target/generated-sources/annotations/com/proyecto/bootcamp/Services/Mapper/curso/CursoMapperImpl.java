package com.proyecto.bootcamp.Services.Mapper.curso;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-03T14:38:33-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
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
        curso.setMaterias( materiaDTOListToMateriaSet( dto.getMaterias() ) );

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
        cursoDTO.setMaterias( materiaSetToMateriaDTOList( entity.getMaterias() ) );

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

    protected Materia materiaDTOToMateria(MateriaDTO materiaDTO) {
        if ( materiaDTO == null ) {
            return null;
        }

        Materia materia = new Materia();

        materia.setId( materiaDTO.getId() );
        materia.setDia( materiaDTO.getDia() );
        materia.setHora( materiaDTO.getHora() );
        materia.setFechafin( materiaDTO.getFechafin() );
        materia.setFechainicio( materiaDTO.getFechainicio() );

        return materia;
    }

    protected Set<Materia> materiaDTOListToMateriaSet(List<MateriaDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<Materia> set = new LinkedHashSet<Materia>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( MateriaDTO materiaDTO : list ) {
            set.add( materiaDTOToMateria( materiaDTO ) );
        }

        return set;
    }

    protected MateriaDTO materiaToMateriaDTO(Materia materia) {
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

    protected List<MateriaDTO> materiaSetToMateriaDTOList(Set<Materia> set) {
        if ( set == null ) {
            return null;
        }

        List<MateriaDTO> list = new ArrayList<MateriaDTO>( set.size() );
        for ( Materia materia : set ) {
            list.add( materiaToMateriaDTO( materia ) );
        }

        return list;
    }
}
