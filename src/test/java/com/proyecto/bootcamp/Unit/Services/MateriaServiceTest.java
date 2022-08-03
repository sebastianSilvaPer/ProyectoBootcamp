package com.proyecto.bootcamp.Unit.Services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.proyecto.bootcamp.Services.MateriaService;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import com.proyecto.bootcamp.Services.Mapper.materia.MateriaMapper;

class MateriaServiceTest{
    @Mock
    MateriaRepository materiaRepository;
    
    @Mock
    MateriaMapper mapper;

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

        when(mapper.dtoToMateria(any(MateriaDTO.class), any(UUID.class))).thenReturn(materia);
        when(mapper.materiaToDTO(any(Materia.class))).thenReturn(materiaDTO);
    }

    @Test
    void saveMateriaShouldReturnSameMateria() {
        when(materiaRepository.save(any(Materia.class))).thenReturn(materia);
        MateriaDTO returned = materiaService.saveMateria(materiaDTO, UUID.randomUUID());
        assertMateriaDTO(returned);
    }

    @Test
    void findAllByCursoIdShouldReturnOneCurso() {
        when(materiaRepository.findAllByCursoId(any(UUID.class))).thenReturn(listMateria);
        List<MateriaDTO> returnedList = materiaService.findAllByCursoId(UUID.randomUUID());
        assertListDto(returnedList);
    }

    @Test
    void updateListShouldReturnthesameList(){
        when(materiaRepository.existsById(any())).thenReturn(true);
        when(materiaRepository.update(any(Materia.class))).thenReturn(materia);

        List<MateriaDTO> returnedList = materiaService.updateList(listDto, UUID.randomUUID());
        assertListDto(returnedList);
    }

    @Test
    void getByIdShouldReturnOneMateria(){
        when(materiaRepository.findById(any(UUID.class))).thenReturn(Optional.of(materia));
        MateriaDTO returned = materiaService.getById(UUID.randomUUID());
        assertMateriaDTO(returned);
    }

    @Test
    void deleteAllCursoIdShouldCallDeleteOnce(){
        materiaService.deleteAllCursoId(UUID.randomUUID());
        verify(materiaRepository, times(1)).deleteAllByCursoId(any(UUID.class));     
    }

    @Test
    void deleteByIdShouldCallDeleteOnce(){
        when(materiaRepository.existsById(any(UUID.class))).thenReturn(true);
        materiaService.deleteById(UUID.randomUUID());
        verify(materiaRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void deleteAllShouldCallDeleteOnce(){
        materiaService.deleteAll();
        verify(materiaRepository, times(1)).deleteAll();
    }

    @Test
    void updateMateriaShouldReturnSameMateria(){
        when(materiaRepository.existsById(any())).thenReturn(true);
        when(materiaRepository.update(any(Materia.class))).thenReturn(materia);
        MateriaDTO returned = materiaService.updateMateria(materiaDTO, UUID.randomUUID(), UUID.randomUUID());
        assertMateriaDTO(returned);
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

        listMateria.add(materia);
        listMateria.add(materia);

        listDto.add(materiaDTO);
        listDto.add(materiaDTO);
    }
    
    private void assertMateriaDTO(MateriaDTO materiaToValid) {
        assertAll(()->{
            assertEquals(materiaToValid.getId(), materiaDTO.getId());
            assertEquals(materiaToValid.getHora(), materiaDTO.getHora());
            assertEquals(materiaToValid.getFechafin(), materiaDTO.getFechafin());
            assertEquals(materiaToValid.getFechainicio(), materiaDTO.getFechainicio());
            assertEquals(materiaToValid.getDia(), materiaDTO.getDia());
        });
    }

    private void assertListDto(List<MateriaDTO> materiasToValid) {
        materiasToValid.forEach(materia -> {
            assertMateriaDTO(materia);
        });
    }
}