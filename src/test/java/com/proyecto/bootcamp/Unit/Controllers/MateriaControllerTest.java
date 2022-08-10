package com.proyecto.bootcamp.Unit.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Services.MateriaService;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class MateriaControllerTest{
    @Autowired 
    MockMvc mockMvc;

    @MockBean
    MateriaService materiaService;

    MateriaDTO dtoToTest = new MateriaDTO();
    List<MateriaDTO> listDTOs = new ArrayList<>();
    UUID cursoId = UUID.randomUUID();

    @BeforeEach
    public void setupEach() {
        MockitoAnnotations.openMocks(this);
        
        dtoToTest.setId(UUID.randomUUID());
        dtoToTest.setDia("lunes");
        dtoToTest.setHora(9);
        dtoToTest.setFechainicio(Date.valueOf("2022-01-01"));
        dtoToTest.setFechafin(Date.valueOf("2022-12-12"));

        addDtos();
    }

    @Test
    public void postMateria_ReturnOkAndAssertMateria_True() throws Exception {
        when(materiaService.saveMateria(any(MateriaDTO.class),any(UUID.class))).thenReturn(dtoToTest);

        dtoToTest.setId(null);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/cursos/"+cursoId.toString()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        
                        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        assertMateria(resultActions);
    }

    @Test
    public void getAll_ReturnOkAndAssertAllMateria_True() throws Exception {
        when(materiaService.findAllByCursoId(any(UUID.class))).thenReturn(listDTOs);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cursos/"+cursoId.toString()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertList(resultActions);
    }

    @Test
    public void putMaterias_ReturnOkAndAssertMateria_True() throws Exception {
        when(materiaService.updateList(anyList(),any(UUID.class))).thenReturn(listDTOs);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/cursos/"+cursoId.toString()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(listDTOs));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertList(resultActions);
    }

    @Test
    public void deleteAllMaterias_ReturnOk_True() throws Exception {
        doNothing().when(materiaService).deleteAllByCursoId(cursoId);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/cursos/"+cursoId.toString()+"/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getMateriaById_ReturnOkAndAssertMateria_True() throws Exception {
        when(materiaService.getById(any(UUID.class))).thenReturn(dtoToTest);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cursos/"+cursoId.toString()+"/materias/"+UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8");

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        
        assertMateria(resultActions);
    }

    @Test
    public void putMateria_ReturnOkAndAssertMateria_True() throws Exception {
        when(materiaService.updateMateria(any(MateriaDTO.class),any(UUID.class),any(UUID.class))).thenReturn(dtoToTest);

        dtoToTest.setId(null);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/cursos/"+cursoId.toString()+"/materias/"+UUID.randomUUID())
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
        doNothing().when(materiaService).deleteById(any(UUID.class));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/cursos/"+cursoId.toString()+"/materias/"+UUID.randomUUID())
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
            assertEquals(materiaDTO.getId(), dtoToTest.getId());
            assertEquals(materiaDTO.getHora(), dtoToTest.getHora());
            assertEquals(materiaDTO.getDia(), dtoToTest.getDia());
            assertEquals(materiaDTO.getFechafin().getClass(), dtoToTest.getFechafin().getClass());
            assertEquals(materiaDTO.getFechainicio().getClass(), dtoToTest.getFechainicio().getClass());

        });
    }

    private void assertList(MvcResult resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        String response = resultActions.getResponse().getContentAsString();
        List<MateriaDTO> materiaDTO = new ObjectMapper().readValue(response, new TypeReference<List<MateriaDTO>>() {});

        materiaDTO.forEach(materia -> {
            assertAll(()->{
                assertEquals(materia.getId(), dtoToTest.getId());
                assertEquals(materia.getHora(), dtoToTest.getHora());
                assertEquals(materia.getDia(), dtoToTest.getDia());
                assertEquals(materia.getFechafin().getClass(), dtoToTest.getFechafin().getClass());
                assertEquals(materia.getFechainicio().getClass(), dtoToTest.getFechainicio().getClass());
            });
        });
    }

    private void addDtos() {
        listDTOs.add(dtoToTest);
        listDTOs.add(dtoToTest);
    } 
}