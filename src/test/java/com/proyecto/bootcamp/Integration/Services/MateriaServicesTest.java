package com.proyecto.bootcamp.Integration.Services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Integration.IT.PostgresContainerTest;
import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.MateriaService;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

@SpringBootTest
@Testcontainers
public class MateriaServicesTest extends PostgresContainerTest{
    @Autowired
    MateriaService materiaServices;

    @Autowired
    CursoServices cursoServices;

    CursoDTO cursoDTO = new CursoDTO();
    MateriaDTO materiaDTO = new MateriaDTO();
    List<MateriaDTO> materialist = new ArrayList<MateriaDTO>();

    @BeforeEach
    void setUp() {
        initDataTest();
    }

    private void initDataTest() {
        materiaDTO.setDia("lunes");
        materiaDTO.setHora(10);
        materiaDTO.setFechafin(Date.valueOf("2023-01-01"));
        materiaDTO.setFechainicio(Date.valueOf("2023-01-01"));
        
        List<CursoDTO> allPaginated = cursoServices.getAllPaginated(0, 1);
        cursoDTO = allPaginated.get(0);
        
        for (int i = 0; i < 5; i++) {
            materialist.add(materiaDTO);    
        }
    }

    @Test
    public void saveMateria_ReturnObjectWithId_True() {
        MateriaDTO saveMateria = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());
        assertTrue(()->saveMateria.getId() != null);
    }

    @Test
    public void findAllByCursoId_ReturnTheSameObject_True() {
        MateriaDTO saveMateria = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());
        MateriaDTO saveMateria2 = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());
        MateriaDTO saveMateria3 = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());

        List<MateriaDTO> allByCursoId = materiaServices.findAllByCursoId(cursoDTO.getId());
        assertAll(()->{
            allByCursoId.contains(saveMateria);
            allByCursoId.contains(saveMateria2);
            allByCursoId.contains(saveMateria3);
        });
    }   

    @Test
    public void getById_ReturnSameSavedObject_True(){
        MateriaDTO saveMateria = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());
        MateriaDTO getById = materiaServices.getById(saveMateria.getId());
        assertEquals(saveMateria, getById);
    }

    @Test
    public void deleteAllCursoId_ReturnListOfSize0_True() {
        materialist.stream()
                    .map(materia -> materiaServices.saveMateria(materia, cursoDTO.getId()))
                    .toList();

        assertTrue(()->materiaServices.findAllByCursoId(cursoDTO.getId()).size() > 0);
        materiaServices.deleteAllByCursoId(cursoDTO.getId());
        assertTrue(()->materiaServices.findAllByCursoId(cursoDTO.getId()).size() == 0);
    }

    @Test
    public void deleteById_ReturnExceptionNotFoundAfterDelete_True() {
        MateriaDTO saveMateria = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());
        assertDoesNotThrow(()-> materiaServices.getById(saveMateria.getId()));
        materiaServices.deleteById(saveMateria.getId());
        assertThrows(NotFoundException.class, ()-> materiaServices.getById(saveMateria.getId()));

    }

    @Test
    public void updateMateria_ReturnNotEqualObject_True() {
        MateriaDTO saveMateria = materiaServices.saveMateria(materiaDTO, cursoDTO.getId());
        materiaDTO.setDia("viernes");
        materiaServices.updateMateria(materiaDTO, saveMateria.getId(), cursoDTO.getId());
        assertNotEquals(saveMateria, materiaDTO);
    }
}
