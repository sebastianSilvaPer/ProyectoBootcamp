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

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T03:11:32-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
public class CursoMapperImpl implements CursoMapper {

    @Override
    public CursoDTO cursoToDTO(Curso curso) {
        if ( curso == null ) {
            return null;
        }

        CursoDTO cursoDTO = new CursoDTO();

        cursoDTO.setId( curso.getId() );
        cursoDTO.setNombre( curso.getNombre() );
        cursoDTO.setDescripcion( curso.getDescripcion() );
        cursoDTO.setMaterias( materiaSetToMateriaDTOList( curso.getMaterias() ) );

        return cursoDTO;
    }

    @Override
    public Curso dtoToCurso(CursoDTO cursoDTO) {
        if ( cursoDTO == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setId( cursoDTO.getId() );
        curso.setNombre( cursoDTO.getNombre() );
        curso.setDescripcion( cursoDTO.getDescripcion() );
        curso.setMaterias( materiaDTOListToMateriaSet( cursoDTO.getMaterias() ) );

        return curso;
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
}
