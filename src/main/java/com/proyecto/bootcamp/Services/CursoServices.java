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
import com.proyecto.bootcamp.Exceptions.UniqueValueException;
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
        checkExistByNombre(cursoDTO.getNombre());
        Curso curso = mapper.mapToEntity(cursoDTO);
        cursoRepository.save(curso);
        return mapper.mapToDto(curso);
    }

    public List<CursoDTO> saveAllCursos(List<CursoDTO> listCursoDtos) {
        listCursoDtos.stream()
                .forEach((cursoDTO) -> {
                    checkExistByNombre(cursoDTO.getNombre());
                });

        List<Curso> listEntity = mapper.mapListToEntity(listCursoDtos);
        Iterable<Curso> savedResult = cursoRepository.saveAll(listEntity);

        List<Curso> listDTOs = iterableToList(savedResult);
        return mapper.mapListToDto(listDTOs);
    }

    public List<CursoDTO> getAll() {
        Iterable<Curso> foundCursos = cursoRepository.findAll();

        List<Curso> listDTOs = iterableToList(foundCursos);
        return mapper.mapListToDto(listDTOs);
    }

    public List<CursoDTO> getAllPaginated(int page, int size) {
        Iterable<Curso> foundCursos = cursoRepository.findAllPaginated(size, (page) * size);

        List<Curso> listDTOs = iterableToList(foundCursos);
        return mapper.mapListToDto(listDTOs);
    }

    public CursoDTO getById(UUID id) {
        Optional<Curso> foundCursoOptional = cursoRepository.findById(id);
        if (!foundCursoOptional.isEmpty()) {
            CursoDTO curso = mapper.mapToDto(foundCursoOptional.get());
            return curso;
        } else {
            throw new NotFoundException("Not found by the given key: " + id);
        }
    }

    public CursoDTO update(CursoDTO cursoDTO) {
        checkExistByNombre(cursoDTO.getNombre());
        Boolean exist = cursoRepository.existsById(cursoDTO.getId());
        if (exist) {
            Curso curso = mapper.mapToEntity(cursoDTO);
            cursoRepository.update(curso);
            return mapper.mapToDto(curso);
        } else {
            throw new NotFoundException("Can't update, curso doesn't exist");
        }
    }

    public void deleteAll() {
        materiaService.deleteAll();
        cursoRepository.deleteAll();
    }

    public void deleteById(UUID id) {
        Boolean exist = cursoRepository.existsById(id);
        if (exist) {
            Curso curso = cursoRepository.findById(id).get();
            materiaService.deleteAllByCursoId(curso.getId());
            cursoRepository.deleteById(id);
        } else {
            throw new NotFoundException("Can't delete, curso doesn't exist");
        }
    }

    public List<CursoDTO> updateList(List<CursoDTO> listCursoDtos) {
        listCursoDtos.stream()
                .forEach((cursoDTO) -> {
                    checkExistById(cursoDTO.getId());
                });

        List<Curso> listEntity = mapper.mapListToEntity(listCursoDtos);
        Iterable<Curso> savedResult = cursoRepository.updateAll(listEntity);

        List<Curso> listDTOs = iterableToList(savedResult);
        return mapper.mapListToDto(listDTOs);
    }

    public void deleteList(List<CursoDTO> listCursoDtos) {
        listCursoDtos.stream()
                .forEach((cursoDTO) -> {
                    checkExistById(cursoDTO.getId());
                });
        List<Curso> listEntity = mapper.mapListToEntity(listCursoDtos);
        cursoRepository.deleteAllList(listEntity);
    }

    public long countAll() {
        return cursoRepository.count();
    }

    public Boolean checkExistById(UUID id) {
        return cursoRepository.existsById(id);
    }

    public void checkExistByNombre(String nombre) {
        if (cursoRepository.existByNombre(nombre))
            throw new UniqueValueException("Curso already exists by name: " + nombre);
    }

    public List<Curso> iterableToList(Iterable<Curso> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }
}
