package com.proyecto.bootcamp.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.DAO.Repositories.CursoRepository;
import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import com.proyecto.bootcamp.Services.Mapper.curso.CursoMapper;

@Service
public class CursoServices {
    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    MateriaService materiaService;

    @Autowired
    CursoMapper mapper;

    public CursoDTO saveCurso(CursoDTO cursoDTO) {
        Curso curso = mapper.mapToEntity(cursoDTO);
        cursoRepository.save(curso);
        return mapper.mapToDto(curso);
    }

    public List<CursoDTO> saveAllCursos(List<CursoDTO> listCursoDtos) {
        List<Curso> listCursos = listCursoDtos.stream()
            .map(mapper::mapToEntity)
            .toList();

        Iterable<Curso> savedResult =  cursoRepository.saveAll(listCursos);

        List<CursoDTO> cursos = StreamSupport.stream(savedResult.spliterator(), false)
                                                        .map(mapper::mapToDto)
                                                        .toList();
        return cursos;
    }

    public List<CursoDTO> getAll(){
        List<CursoDTO> cursos = StreamSupport.stream(cursoRepository.findAll().spliterator(), false)
                                                        .map(mapper::mapToDto)
                                                        .toList();
        return cursos;                                                
    }

    public List<CursoDTO> getAllPaginated(int page, int size){
        
        List<CursoDTO> cursos = StreamSupport.stream(cursoRepository.findAllPaginated(size, (page)*size).spliterator(), false)
                                            .map(mapper::mapToDto)
                                            .toList();
        return cursos;                                                
    }

     public CursoDTO getById(UUID id){
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        if(!cursoOpt.isEmpty()){
            CursoDTO curso = mapper.mapToDto(cursoOpt.get());
            return curso;
        }else{
            throw new NotFoundException("Not found by the given key: "+id);
        }
    }

    public CursoDTO update(CursoDTO cursoDTO){
        Boolean exist = cursoRepository.existsById(cursoDTO.getId());
        if(exist){
            Curso curso = mapper.mapToEntity(cursoDTO);
            cursoRepository.update(curso);
            return mapper.mapToDto(curso);
        }else{
            throw new NotFoundException("Can't update, curso doesn't exist");
        }
    }

    public void deleteAll() {
        materiaService.deleteAll();
        cursoRepository.deleteAll();
    } 

    public void deleteById(UUID id) {
        Boolean exist = cursoRepository.existsById(id);
        if(exist){
            Curso curso = cursoRepository.findById(id).get();
            materiaService.deleteAllCursoId(curso.getId());
            cursoRepository.deleteById(id);
        }else{
            throw new NotFoundException("Can't delete, curso doesn't exist");
        }
    }

    public long countAll(){
        return cursoRepository.count();
    }
}
