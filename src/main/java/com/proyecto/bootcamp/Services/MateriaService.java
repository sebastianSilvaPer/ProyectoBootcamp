package com.proyecto.bootcamp.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.DAO.Repositories.MateriaRepository;
import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTOWithCurso;
import com.proyecto.bootcamp.Services.Mapper.MateriaMapper;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;
    
    MateriaMapper mapper = MateriaMapper.INSTANCE;

    public MateriaDTOWithCurso saveMateria(MateriaDTOWithCurso materiaDTO) {
        Materia materiaModel = mapper.dtoWithCursoToMateria(materiaDTO);
        materiaRepository.save(materiaModel);
        return mapper.materiaToDTOWithCurso(materiaModel);
    }

    public List<MateriaDTOWithCurso> saveAllMateria(List<MateriaDTOWithCurso> listMateriaDTO) {
        List<Materia> listMateria = listMateriaDTO.stream()
            .map(mapper::dtoWithCursoToMateria)
            .toList();

        Iterable<Materia> savedResult =  materiaRepository.saveAll(listMateria);

        List<MateriaDTOWithCurso> materias = StreamSupport.stream(savedResult.spliterator(), false)
                                                        .map(mapper::materiaToDTOWithCurso)
                                                        .toList();
        return materias;
    }

    public List<MateriaDTOWithCurso> getAll(){
        List<MateriaDTOWithCurso> materias = StreamSupport.stream(materiaRepository.findAll().spliterator(), false)
                                                        .map(mapper::materiaToDTOWithCurso)
                                                        .toList();
        return materias;                                                
    }

    public MateriaDTOWithCurso getById(UUID id){
        Optional<Materia> materiaOpt = materiaRepository.findById(id);
        if(!materiaOpt.isEmpty()){
            return mapper.materiaToDTOWithCurso(materiaOpt.get());
        }else{
            throw new NotFoundException("Not found by the given key: "+id);
        }
    }

    public List<MateriaDTOWithCurso> getAllPaginated(int page, int size){
        
        List<MateriaDTOWithCurso> materias = StreamSupport.stream(materiaRepository.findAllPaginated(size, (page)*size).spliterator(), false)
                                                        .map(mapper::materiaToDTOWithCurso)
                                                        .toList();
    
        return materias;                                                
    }

    public MateriaDTOWithCurso update(MateriaDTOWithCurso materiaDTO){
        Boolean exist = materiaRepository.existsById(materiaDTO.getId());
        if(exist){
            Materia materia = mapper.dtoWithCursoToMateria(materiaDTO);
            materiaRepository.update(materia);
            return mapper.materiaToDTOWithCurso(materia);
        }else{
            throw new NotFoundException("Can't update, materia doesn't exist");
        }
    }

    public void deleteAll(List<MateriaDTOWithCurso> listMateriaDTO) {
        Boolean exist = listMateriaDTO.stream().allMatch(x->materiaRepository.existsById(x.getId()));                                                    
        
        if(exist){
            List<Materia> materiasList = listMateriaDTO.stream()
                                                    .map(mapper::dtoWithCursoToMateria)
                                                    .toList();
        
            materiaRepository.deleteAll(materiasList);
        }else{
            throw new NotFoundException("Can't delete, materia doesn't exist");
        }
    }

    public void delete(MateriaDTOWithCurso materiaDTO) {
        Boolean exist = materiaRepository.existsById(materiaDTO.getId());
        if(exist){
            Materia materia = mapper.dtoWithCursoToMateria(materiaDTO);
            materiaRepository.delete(materia);
        }else{
            throw new NotFoundException("Can't delete, materia doesn't exist");
        }
    }

    public void deleteById(UUID id) {
        Boolean exist = materiaRepository.existsById(id);
        if(exist){
            materiaRepository.deleteById(id);
        }else{
            throw new NotFoundException("Can't delete, materia doesn't exist");
        }
    }
    
    public List<MateriaDTO> findAllByCursoId(UUID id) {
        List<MateriaDTO> materias = StreamSupport.stream(materiaRepository.findAllByCursoId(id).spliterator(), false)
                                                        .map(mapper::materiaToDTO)
                                                        .toList();   
        return materias;
    }
}
