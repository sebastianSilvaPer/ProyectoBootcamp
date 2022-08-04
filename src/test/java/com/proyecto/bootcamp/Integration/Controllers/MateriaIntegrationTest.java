package com.proyecto.bootcamp.Integration.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

import com.proyecto.bootcamp.Services.MateriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@Testcontainers
class MateriaIntegrationTest{
    @Autowired 
    MockMvc mockMvc;

    @Autowired
    MateriaService materiaService;

    @Autowired
    CursoServices cursoServices;

    MateriaDTO dtoToTest = new MateriaDTO();
    List<MateriaDTO> listDTOs = new ArrayList<>();
    CursoDTO cursoDto = new CursoDTO();

    @BeforeEach
    public void setupEach() {
        dtoToTest.setDia("lunes");
        dtoToTest.setHora(9);
        dtoToTest.setFechainicio(Date.valueOf("2022-01-01"));
        dtoToTest.setFechafin(Date.valueOf("2022-12-12"));

        cursoDto = cursoServices.getAllPaginated(0,1).get(0);
        addDtos();
    }

    private MateriaDTO getMateria(){
        MateriaDTO savedMateria = materiaService.saveMateria(dtoToTest, cursoDto.getId());
        return savedMateria;
    }

    @Test
    public void postMateria_ReturnOkAndAssertMateria_True() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/cursos/"+cursoDto.getId()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertMateria(resultActions);
    }

    @Test
    public void getAll_ReturnOkAndAssertMateriaIdOnResponse_True() throws Exception {
        MateriaDTO materia = getMateria();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cursos/"+cursoDto.getId()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        String response = resultActions.getResponse().getContentAsString();
        List<MateriaDTO> materiasResult = new ObjectMapper().readValue(response, new TypeReference<List<MateriaDTO>>() {});

        assertTrue( materiasResult.stream()
                    .anyMatch(materiaResult -> materiaResult.getId().equals( materia.getId())));
    }

    @Test
    public void deleteAllMaterias_ReturnOk_True() throws Exception {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/cursos/"+cursoDto.getId()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getMateriaById_ReturnOkAndAssertMateria_True() throws Exception {
        MateriaDTO materia = getMateria();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cursos/"+cursoDto.getId()+"/materias/"+materia.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
            String response = resultActions.getResponse().getContentAsString();
        MateriaDTO materiaDTO = new ObjectMapper().readValue(response, new TypeReference<MateriaDTO>() {});

        assertAll(()->{
            assertEquals(materia.getId(), materiaDTO.getId());
            assertEquals(materia.getHora(), materiaDTO.getHora());
            assertEquals(materia.getDia(), materiaDTO.getDia());
        });
        
        assertMateria(resultActions);
    }

    @Test
    public void putMateria_ReturnOkAndAssertMateriaModified_True() throws Exception {
        MateriaDTO materia = getMateria();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/cursos/"+cursoDto.getId()+"/materias/"+materia.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        assertMateria(resultActions);
    }

    @Test
    public void deleteMateria_ReturnOk_True() throws Exception {
        MateriaDTO materia = getMateria();

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/cursos/"+cursoDto.getId()+"/materias/"+materia.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private void assertMateria(MvcResult resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        String response = resultActions.getResponse().getContentAsString();

        MateriaDTO materiaDTO = new ObjectMapper().readValue(response, MateriaDTO.class);
        assertAll(()->{
            assertEquals(materiaDTO.getHora(), dtoToTest.getHora());
            assertEquals(materiaDTO.getDia(), dtoToTest.getDia());
        });
    }

    private void addDtos() {
        listDTOs.add(dtoToTest);
        listDTOs.add(dtoToTest);
    } 
}