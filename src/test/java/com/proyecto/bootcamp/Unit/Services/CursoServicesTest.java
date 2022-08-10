package com.proyecto.bootcamp.Unit.Services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.DAO.Repositories.CursoRepository;
import com.proyecto.bootcamp.Exceptions.UniqueValueException;
import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.MateriaService;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import com.proyecto.bootcamp.Services.Mapper.curso.CursoMapper;

class CursoServicesTest{
    @Mock
    CursoRepository cursoRepository;
    
    @Mock
    MateriaService materiaService;

    @Mock
    CursoMapper cursoMapper;

    @InjectMocks
    CursoServices cursoServices;
    
    Curso cursoToTest = new Curso();
    CursoDTO dtoToTest = new CursoDTO();
    
    List<Curso> cursosList = new ArrayList<>();
    List<CursoDTO> cursosDTO =  new ArrayList<>();
    
    
    @BeforeEach
    void setUp() {        
        MockitoAnnotations.openMocks(this);
    
        cursoToTest.setNombre("Test curso");
        cursoToTest.setDescripcion("Testing curso");

        dtoToTest.setNombre("Test curso");
        dtoToTest.setDescripcion("Testing curso");
    
        this.addCursos();
        this.addDtos();

        when(cursoMapper.mapToDto(any(Curso.class))).thenReturn(dtoToTest);
        when(cursoMapper.mapToEntity(any(CursoDTO.class))).thenReturn(cursoToTest);
    }

    @Test
    void saveCurso_ReturnSameCurso_True() {
        when(cursoRepository.existByNombre(anyString())).thenReturn(false);
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoToTest);
        CursoDTO savedCurso = cursoServices.saveCurso(dtoToTest);
        assertDto(savedCurso);
    }

    @Test
    void saveAllCursos_ReturnSameSavedCursos_True() {
        when(cursoRepository.existByNombre(anyString())).thenReturn(false);
        when(cursoRepository.saveAll(anyIterable())).thenReturn((Iterable<Curso>)cursosList);

        List<CursoDTO> returned = cursoServices.saveAllCursos(cursosDTO);
        
        returned.forEach(cursoDto -> {
            assertDto(cursoDto);
        });
    }

    @Test
    void getAll_ReturnSameAllCursos_True() {
        when(cursoRepository.findAll()).thenReturn((Iterable<Curso>)cursosList);
        
        List<CursoDTO> returned = cursoServices.getAll();

        returned.forEach(cursoDto -> {
            assertDto(cursoDto);
        });
    }

    @Test
    void getAllPaginated_ReturnSameListCursos_True() {
        when(cursoRepository.findAllPaginated(anyInt(), anyInt())).thenReturn((Iterable<Curso>)cursosList);
        
        List<CursoDTO> returned = cursoServices.getAllPaginated(1, 10);

        returned.forEach(cursoDto -> {
            assertDto(cursoDto);
        });
    }

    @Test
    void getById_ReturnSameCurso_True() {
        when(cursoRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(cursoToTest));
        CursoDTO returned = cursoServices.getById(UUID.randomUUID());
        assertDto(returned);
    }

    @Test
    void update_ReturnSameUpdatedCurso_True() {
        when(cursoRepository.existByNombre(anyString())).thenReturn(false);
        when(cursoRepository.existsById(any())).thenReturn(true);
        when(cursoRepository.update(any(Curso.class))).thenReturn(cursoToTest);

        CursoDTO returned = cursoServices.update(dtoToTest);
        assertDto(returned);
    }

    @Test
    void testDeleteAll_VerifyDeleteMethodsCall_True() {
        cursoServices.deleteAll();
        verify(materiaService, times(1)).deleteAll();
        verify(cursoRepository, times(1)).deleteAll();
    }

    @Test
    void testDeleteById_VerifyDeleteMethodsCall_True() {
        when(cursoRepository.existsById(any())).thenReturn(true);
        when(cursoRepository.findById(any())).thenReturn(Optional.of(cursoToTest));
        
        cursoServices.deleteById(UUID.randomUUID());
        verify(materiaService, times(1)).deleteAllByCursoId(any());
        verify(cursoRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void checkExistByNombre_ThrowsUniqueValueException() {
        when(cursoRepository.existByNombre(anyString())).thenReturn(true);
        assertThrows(UniqueValueException.class, () -> cursoServices.checkExistByNombre("Test curso"));
    }

    private void assertDto(CursoDTO cursoDTO) {
        assertAll(()->{
            assertEquals(cursoDTO.getNombre(), dtoToTest.getNombre());
            assertEquals(cursoDTO.getDescripcion(), dtoToTest.getDescripcion());
        });
    }

    private void addCursos() {
        cursosList.add(cursoToTest);
        cursosList.add(cursoToTest);
        cursosList.add(cursoToTest);
        cursosList.add(cursoToTest);
        cursosList.add(cursoToTest);
    }

    private void addDtos() {
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
    }
}