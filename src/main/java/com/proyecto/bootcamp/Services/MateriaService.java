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
import com.proyecto.bootcamp.Services.Mapper.materia.MateriaMapper;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    MateriaMapper mapper;

    public List<MateriaDTO> iterableToListDto(Iterable<Materia> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .map(mapper::materiaToDTO)
                .toList();
    }

    public MateriaDTO saveMateria(MateriaDTO materiaDTO, UUID cursoID) {
        Materia materiaModel = mapper.dtoToMateria(materiaDTO, cursoID);
        materiaRepository.save(materiaModel);
        return mapper.materiaToDTO(materiaModel);
    }

    public List<MateriaDTO> findAllByCursoId(UUID id) {
        Iterable<Materia> foundCursos = materiaRepository.findAllByCursoId(id);
        return iterableToListDto(foundCursos);
    }

    public List<MateriaDTO> findAll(int page, int size) {
        Iterable<Materia> foundMaterias = materiaRepository.findAllPaginated(size, (page) * size);
        return iterableToListDto(foundMaterias);
    }

    public List<MateriaDTO> updateList(List<MateriaDTO> listDTOs, UUID cursoId) {
        Boolean exists = listDTOs.stream()
                .allMatch(materia -> materiaRepository.existsById(materia.getId()));

        if (exists) {
            return listDTOs.stream()
                    .map(materiaDTO -> {
                        Materia materiaModel = mapper.dtoToMateria(materiaDTO, cursoId);
                        materiaModel = materiaRepository.update(materiaModel);
                        return materiaModel;
                    })
                    .map(mapper::materiaToDTO)
                    .toList();
        } else {
            throw new NotFoundException("Can't update, one of the materias doesn't exist");
        }
    }

    public void deleteById(UUID id) {
        Boolean exist = materiaRepository.existsById(id);
        if (exist) {
            materiaRepository.deleteById(id);
        } else {
            throw new NotFoundException("Can't delete, materia doesn't exist");
        }
    }

    public MateriaDTO getById(UUID id) {
        Optional<Materia> materiaOpt = materiaRepository.findById(id);

        if (!materiaOpt.isEmpty()) {
            return mapper.materiaToDTO(materiaOpt.get());
        } else {
            throw new NotFoundException("Not found by the given key: " + id);
        }
    }

    public void deleteAllByCursoId(UUID cursoId) {
        materiaRepository.deleteAllByCursoId(cursoId);
    }

    public void deleteAll() {
        materiaRepository.deleteAll();
    }

    public MateriaDTO updateMateria(MateriaDTO materiaDTO, UUID id, UUID cursoId) {
        materiaDTO.setId(id);
        Boolean exist = materiaRepository.existsById(id);
        if (exist) {
            Materia materia = mapper.dtoToMateria(materiaDTO, cursoId);
            materiaRepository.update(materia);
            return mapper.materiaToDTO(materia);
        } else {
            throw new NotFoundException("Can't update, materia doesn't exist");
        }
    }
}
