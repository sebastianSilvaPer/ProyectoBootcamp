package com.proyecto.bootcamp.Services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyIterable;
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
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;

class CursoServicesTest{
    @Mock
    CursoRepository cursoRepository;
    
    @Mock
    MateriaService materiaService;

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

        when(materiaService.findAllByCursoId(any(UUID.class))).thenReturn(null);
    }

    @Test
    void saveCurso() {
        when(cursoRepository.save(any(Curso.class))).thenReturn(cursoToTest);
        CursoDTO savedCurso = cursoServices.saveCurso(dtoToTest);
        assertAll(()->{
            assertEquals(savedCurso.getNombre(), cursoToTest.getNombre());
            assertEquals(savedCurso.getDescripcion(), cursoToTest.getDescripcion());
        }); 
    }

    @Test
    void saveAllCursos() {
        when(cursoRepository.saveAll(anyIterable())).thenReturn((Iterable<Curso>)cursosList);
        List<CursoDTO> returned = cursoServices.saveAllCursos(cursosDTO);
        assertAll(()->{
            returned.stream().forEach(x->{
                assertEquals(x.getDescripcion(), "test");
                assertTrue(x instanceof CursoDTO);
            });
        });
    }

    @Test
    void getAll() {
        when(cursoRepository.findAll()).thenReturn((Iterable<Curso>)cursosList);
        
        List<CursoDTO> returned =cursoServices.getAll();
        assertAll(()->{
            returned.stream().forEach(x->{
                assertEquals(x.getDescripcion(), "test");
                assertTrue(x instanceof CursoDTO);
            });
        });
    }

    @Test
    void getAllPaginated() {
        when(cursoRepository.findAllPaginated(anyInt(), anyInt())).thenReturn((Iterable<Curso>)cursosList);
        List<CursoDTO> returned =cursoServices.getAllPaginated(1,1);
        assertAll(()->{
            returned.stream().forEach(x->{
                assertEquals(x.getDescripcion(), "test");
                assertTrue(x instanceof CursoDTO);
            });
        });
    }

    @Test
    void getById() {
        when(cursoRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(cursoToTest));
        CursoDTO returned = cursoServices.getById(UUID.randomUUID());
        assertAll(()->{
            assertEquals(returned.getNombre(), cursoToTest.getNombre());
            assertEquals(returned.getDescripcion(), cursoToTest.getDescripcion());
        });
    }

    @Test
    void update() {
        when(cursoRepository.update(any(Curso.class))).thenReturn(cursoToTest);
        CursoDTO savedCurso = cursoServices.saveCurso(dtoToTest);
        assertAll(()->{
            assertEquals(savedCurso.getNombre(), cursoToTest.getNombre());
            assertEquals(savedCurso.getDescripcion(), cursoToTest.getDescripcion());
        }); 
    }

    // @Test
    // void testDeleteAll() {
    // }

    // @Test
    // void testDelete() {
    // }

    // @Test
    // void testDeleteById() {
    // }

    private void addCursos() {
        Curso test1 = new Curso(); 
        test1.setNombre("1");
        test1.setDescripcion("test");
        Curso test2 = new Curso();
        test2.setNombre("2");
        test2.setDescripcion("test");
        Curso test3 = new Curso();
        test3.setNombre("3");
        test3.setDescripcion("test");
        Curso test4 = new Curso();
        test4.setNombre("4");
        test4.setDescripcion("test");
        Curso test5 = new Curso();
        test5.setNombre("5");
        test5.setDescripcion("test");
        cursosList.add(test1);
        cursosList.add(test2);
        cursosList.add(test3);
        cursosList.add(test4);
        cursosList.add(test5);
    }

    private void addDtos() {
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "1","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "2","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "3","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "4","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "5","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "6","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "7","test",new ArrayList<>()));
        cursosDTO.add(new CursoDTO(UUID.randomUUID(), "8","test",new ArrayList<>()));
    }
}