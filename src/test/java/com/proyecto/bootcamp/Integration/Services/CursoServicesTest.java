package com.proyecto.bootcamp.Integration.Services;

import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Exceptions.UniqueValueException;
import com.proyecto.bootcamp.Integration.IT.PostgresContainerTest;
import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
public class CursoServicesTest extends PostgresContainerTest {
    @Autowired
    CursoServices cursoServices;

    CursoDTO cursoDTO = new CursoDTO();
    List<CursoDTO> cursolist = new ArrayList<CursoDTO>();

    @BeforeEach
    void setUp() {
        initDataTest();
    }

    private void initDataTest() {
        cursoDTO.setNombre("Curso de Java");
        cursoDTO.setDescripcion("Curso de Java");
        for (int i = 0; i < 5; i++) {
            cursolist.add(cursoDTO);    
        }
    }

    @Test
    void saveCurso_ReturnCursoWithIdNotNull_True() {
        cursoDTO.setNombre(UUID.randomUUID().toString());
        CursoDTO saveCurso = cursoServices.saveCurso(cursoDTO);
        assertTrue(saveCurso.getId() != null);
    }

    @Test
    void saveCurso_ReturnSameSavedCurso_True() {
        CursoDTO saveCurso = cursoServices.saveCurso(cursoDTO);
        assertCurso(saveCurso, cursoDTO);
    }

    @Test
    void saveAllCursos_ReturnListSameSizeAndIds_True() {
        cursolist.forEach(curso -> {
            curso.setNombre(UUID.randomUUID().toString());
        });
        List<CursoDTO> saveAllCursos = cursoServices.saveAllCursos(cursolist);
        assertAll(() -> {
            assertEquals(saveAllCursos.size(), cursolist.size());
            saveAllCursos.stream().allMatch(curso -> curso.getId() != null);
        });
    }

    @Test
    void getAll_ReturnListSameSizeAsCount_True(){
        List<CursoDTO> allCursos = cursoServices.getAll();
        assertAll(() -> {
            assertEquals(allCursos.size(), cursoServices.countAll());
            
        });
    }

    @Test
    void getAllPaginated_ReturnListOfSizeEqual3_True(){
        List<CursoDTO> allCursos = cursoServices.getAllPaginated(1, 3);
        assertAll(() -> {
            assertEquals(allCursos.size(), 3);
        });
    }

    @Test
    void getById_ReturnSameCursoAsSavedAfterGet_True(){
        cursoDTO.setNombre(UUID.randomUUID().toString());
        CursoDTO saveCurso = cursoServices.saveCurso(cursoDTO);
        CursoDTO curso = cursoServices.getById(saveCurso.getId());
        assertCurso(curso, saveCurso);
    }

    @Test
    void update(){
        CursoDTO saveCurso = cursoServices.saveCurso(cursoDTO);
        saveCurso.setNombre(UUID.randomUUID().toString());
        CursoDTO curso = cursoServices.update(saveCurso);
        assertFalse(curso.getNombre().equals(cursoDTO.getNombre()));
    }

    @Test
    void deleteById() {
        cursolist.forEach(curso -> {
            curso.setNombre(UUID.randomUUID().toString());
        });
        List<CursoDTO> saveAllCursos = cursoServices.saveAllCursos(cursolist);
        assertAll(() -> {
            saveAllCursos.stream().forEach(curso -> {
                assertDoesNotThrow(()->cursoServices.getById(curso.getId())); 
            });
        });
        saveAllCursos.stream().forEach(curso -> cursoServices.deleteById(curso.getId()));
        assertAll(() -> {
            saveAllCursos.stream().forEach(curso -> {
                assertThrows(NotFoundException.class, ()->cursoServices.getById(curso.getId()));
            });
        });
    }

    @Test
    void checkExistByNombre_ThrowsUniqueValueException() {
        String nombre = UUID.randomUUID().toString();
        cursoDTO.setNombre(nombre);
        cursoServices.saveCurso(cursoDTO);
        assertThrows(UniqueValueException.class, () -> cursoServices.checkExistByNombre(nombre));
    }
    
    public void assertCurso(CursoDTO cursoDTO,CursoDTO compare) {
        assertAll(()->{
            assertEquals(cursoDTO.getNombre(), compare.getNombre());
            assertEquals(cursoDTO.getDescripcion(), compare.getDescripcion());
        });
    }
}