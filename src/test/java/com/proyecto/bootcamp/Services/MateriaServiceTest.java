package com.proyecto.bootcamp.Services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.DAO.Repositories.MateriaRepository;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

class MateriaServiceTest{
    /* @Mock
    MateriaRepository materiaRepository;

    @InjectMocks
    MateriaService materiaService;

    Materia materia = new Materia();
    MateriaDTO materiaDTO = new MateriaDTO();

    List<Materia> listMateria = new ArrayList<>();
    List<MateriaDTO> listDto = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        initDataTest();
    }

    @Test
    void saveMateria() {
        when(materiaRepository.save(any(Materia.class))).thenReturn(materia);
        
        MateriaDTOWithCurso savedMateria = materiaService.saveMateria(materiaDTOWithCurso);
        assertMateriaDTOWithCurso(savedMateria);
    }

    @Test
    void saveAllMateria() {
        when(materiaRepository.saveAll(anyIterable())).thenReturn((Iterable<Materia>)listMateria);
        
        List<MateriaDTOWithCurso> returned = materiaService.saveAllMateria(listDtoCurso);
        returned.forEach(materia->{
            assertMateriaDTOWithCurso(materia);
        });
    }

    @Test
    void getAll() {
        when(materiaRepository.findAll()).thenReturn((Iterable<Materia>)listMateria);
        
        List<MateriaDTOWithCurso> returned = materiaService.getAll();
        returned.forEach(materia->{
            assertMateriaDTOWithCurso(materia);
        });
    }

    @Test
    void getById() {
        when(materiaRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(materia));
        MateriaDTOWithCurso returned = materiaService.getById(UUID.randomUUID());
        assertMateriaDTOWithCurso(returned);
    }

    @Test
    void getAllPaginated() {
        when(materiaRepository.findAllPaginated(anyInt(),anyInt())).thenReturn((Iterable<Materia>)listMateria);
        
        List<MateriaDTOWithCurso> returned = materiaService.getAllPaginated(1,1);
        returned.forEach(materia->{
            assertMateriaDTOWithCurso(materia);
        });
    }

    @Test
    void update() {
        when(materiaRepository.update(any(Materia.class))).thenReturn(materia);
        MateriaDTOWithCurso returned = materiaService.saveMateria(materiaDTOWithCurso);
        assertMateriaDTOWithCurso(returned);
    }

    @Test
    void testDeleteAll() {
        doNothing().when(materiaRepository).deleteAll();
        when(materiaRepository.existsById(any(UUID.class))).thenReturn(true);
        assertDoesNotThrow(()->materiaService.deleteAll(listDtoCurso));
    }

    @Test
    void testDelete() {
        doNothing().when(materiaRepository).delete(any(Materia.class));
        when(materiaRepository.existsById(any(UUID.class))).thenReturn(true);
        assertDoesNotThrow(()->materiaService.delete(materiaDTOWithCurso));
    }

    @Test
    void testDeleteById() {
        doNothing().when(materiaRepository).deleteById(any(UUID.class));
        when(materiaRepository.existsById(any(UUID.class))).thenReturn(true);
        assertDoesNotThrow(()->materiaService.deleteById(materiaDTOWithCurso.getId()));
    }

    @Test
    void findAllByCursoId() {
        when(materiaRepository.findAllByCursoId(any(UUID.class))).thenReturn((Iterable<Materia>)listMateria);
        
        List<MateriaDTO> returned = materiaService.findAllByCursoId(materiaDTOWithCurso.getCurso());
        returned.forEach(materia->{
            assertMateriaDTO(materia);
        });
    }

    private void assertMateriaDTO(MateriaDTO materiaToValid) {
        assertAll(()->{
            assertEquals(materiaToValid.getId(), materiaDTO.getId());
            assertEquals(materiaToValid.getHora(), materiaDTO.getHora());
            assertEquals(materiaToValid.getFechafin(), materiaDTO.getFechafin());
            assertEquals(materiaToValid.getFechainicio(), materiaDTO.getFechainicio());
            assertEquals(materiaToValid.getDia(), materiaDTO.getDia());
            assertEquals(materiaToValid.getId(), materiaDTO.getId());
        });
    }

    private void assertMateriaDTOWithCurso(MateriaDTOWithCurso materiaToValid) {
        assertAll(()->{
            assertEquals(materiaToValid.getId(), materiaDTOWithCurso.getId());
            assertEquals(materiaToValid.getHora(), materiaDTOWithCurso.getHora());
            assertEquals(materiaToValid.getFechafin(), materiaDTOWithCurso.getFechafin());
            assertEquals(materiaToValid.getFechainicio(), materiaDTOWithCurso.getFechainicio());
            assertEquals(materiaToValid.getDia(), materiaDTOWithCurso.getDia());
            assertEquals(materiaToValid.getCurso(), materiaDTOWithCurso.getCurso());
            assertEquals(materiaToValid.getId(), materiaDTOWithCurso.getId());
        });
    }

    private void initDataTest() {
        UUID id = UUID.randomUUID();
        UUID curso_id = UUID.randomUUID();

        materia.setId(id);
        materia.setDia("test");
        materia.setHora(8);
        materia.setFechainicio(Date.valueOf("2022-01-01"));
        materia.setFechafin(Date.valueOf("2022-01-01"));
        materia.setCurso(curso_id);

        materiaDTO.setId(id);
        materiaDTO.setDia("test");
        materiaDTO.setHora(8);
        materiaDTO.setFechainicio(Date.valueOf("2022-01-01"));
        materiaDTO.setFechafin(Date.valueOf("2022-01-01"));
        
        materiaDTOWithCurso.setId(id);
        materiaDTOWithCurso.setDia("test");
        materiaDTOWithCurso.setHora(8);
        materiaDTOWithCurso.setFechainicio(Date.valueOf("2022-01-01"));
        materiaDTOWithCurso.setFechafin(Date.valueOf("2022-01-01"));
        materiaDTOWithCurso.setCurso(curso_id);

        listMateria.add(materia);
        listMateria.add(materia);

        listDto.add(materiaDTO);
        listDto.add(materiaDTO);

        listDtoCurso.add(materiaDTOWithCurso);
        listDtoCurso.add(materiaDTOWithCurso);
    } */
}