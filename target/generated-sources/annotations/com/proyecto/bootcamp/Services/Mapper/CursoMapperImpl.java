package com.proyecto.bootcamp.Services.Mapper;

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
    date = "2022-07-29T14:53:37-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class CursoMapperImpl implements CursoMapper {

    @Override
    public CursoDTO cursoToDTO(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoDTO cursoDTO = new CursoDTO();

        cursoDTO.setDescripcion( curso.getDescripcion() );
        cursoDTO.setId( curso.getId() );
        cursoDTO.setMaterias( materiaSetToMateriaDTOList( curso.getMaterias() ) );
        cursoDTO.setNombre( curso.getNombre() );

        return cursoDTO;
    }

    @Override
    public Curso dtoToCurso(CursoDTO cursoDTO) {
        if ( cursoDTO == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setDescripcion( cursoDTO.getDescripcion() );
        curso.setId( cursoDTO.getId() );
        curso.setMaterias( materiaDTOListToMateriaSet( cursoDTO.getMaterias() ) );
        curso.setNombre( cursoDTO.getNombre() );

        return curso;
    }

    protected MateriaDTO materiaToMateriaDTO(Materia materia) {
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

    protected Materia materiaDTOToMateria(MateriaDTO materiaDTO) {
        if ( materiaDTO == null ) {
            return null;
        }

        Materia materia = new Materia();

        materia.setDia( materiaDTO.getDia() );
        materia.setFechafin( materiaDTO.getFechafin() );
        materia.setFechainicio( materiaDTO.getFechainicio() );
        materia.setHora( materiaDTO.getHora() );
        materia.setId( materiaDTO.getId() );

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
}
