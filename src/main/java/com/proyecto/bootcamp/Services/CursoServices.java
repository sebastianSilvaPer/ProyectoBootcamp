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
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTOWithCurso;
import com.proyecto.bootcamp.Services.Mapper.CursoMapper;

@Service
public class CursoServices {
    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    MateriaService materiaService;

    @Autowired
    CursoMapper mapper;
    
    public CursoDTO saveCurso(CursoDTO cursoDTO) {
        Curso curso = mapper.dtoToCurso(cursoDTO);
        cursoRepository.save(curso);
        return mapper.cursoToDTO(curso);
    }

    public List<CursoDTO> saveAllCursos(List<CursoDTO> listCursoDtos) {
        List<Curso> listCursos = listCursoDtos.stream()
            .map(mapper::dtoToCurso)
            .toList();

        Iterable<Curso> savedResult =  cursoRepository.saveAll(listCursos);

        List<CursoDTO> materias = StreamSupport.stream(savedResult.spliterator(), false)
                                                        .map(mapper::cursoToDTO)
                                                        .toList();
        return materias;
    }

    public List<CursoDTO> getAll(){
        List<CursoDTO> cursos = StreamSupport.stream(cursoRepository.findAll().spliterator(), false)
                                                        .map(mapper::cursoToDTO)
                                                        .toList();
        cursos.stream().forEach(curso->{
            curso.setMaterias(materiaService.findAllByCursoId(curso.getId()));
        });
        return cursos;                                                
    }

    public List<CursoDTO> getAllPaginated(int page, int size){
        
        List<CursoDTO> cursos = StreamSupport.stream(cursoRepository.findAllPaginated(size, (page)*size).spliterator(), false)
                                            .map(mapper::cursoToDTO)
                                            .toList();
        cursos.stream().forEach(curso->{
            curso.setMaterias(materiaService.findAllByCursoId(curso.getId()));
        });
        return cursos;                                                
    }

     public CursoDTO getById(UUID id){
        Optional<Curso> cursoOpt = cursoRepository.findById(id);
        if(!cursoOpt.isEmpty()){
            CursoDTO curso = mapper.cursoToDTO(cursoOpt.get());
            curso.setMaterias(materiaService.findAllByCursoId(id));
            return curso;
        }else{
            throw new NotFoundException("Not found by the given key: "+id);
        }
    }

    public CursoDTO update(CursoDTO cursoDTO){
        Boolean exist = cursoRepository.existsById(cursoDTO.getId());
        if(exist){
            Curso curso = mapper.dtoToCurso(cursoDTO);
            cursoRepository.update(curso);
            return mapper.cursoToDTO(curso);
        }else{
            throw new NotFoundException("Can't update, curso doesn't exist");
        }
    }

    public void deleteAll(List<CursoDTO> listCursoDTO) {
        Boolean exist = listCursoDTO.stream().allMatch(curso->cursoRepository.existsById(curso.getId()));                                                    
        
        if(exist){
            listCursoDTO.stream()
            .forEach(this::delete);
        }else{
            throw new NotFoundException("Can't delete, curso doesn't exist");
        }
    }

    public void delete(CursoDTO cursoDTO) {
        Boolean exist = cursoRepository.existsById(cursoDTO.getId());
        if(exist){
            List<MateriaDTOWithCurso> materias = cursoDTO.getMaterias()
                                                    .stream()
                                                    .map(materia->materiaService.mapper.dtoToWithCurso(materia, cursoDTO.getId()))
                                                    .toList();
            materiaService.deleteAll(materias);
            Curso curso = mapper.dtoToCurso(cursoDTO);
            cursoRepository.delete(curso);
        }else{
            throw new NotFoundException("Can't delete, curso doesn't exist");
        }
    }

    public void deleteById(UUID id) {
        Boolean exist = cursoRepository.existsById(id);
        if(exist){
            Curso curso = cursoRepository.findById(id).get();
            List<MateriaDTOWithCurso> materias = curso 
                            .getMaterias()
                            .stream()
                            .map(materiaService.mapper::materiaToDTOWithCurso)
                            .toList();
            
            materiaService.deleteAll(materias);
            cursoRepository.deleteById(id);
        }else{
            throw new NotFoundException("Can't delete, curso doesn't exist");
        }
    }
}
