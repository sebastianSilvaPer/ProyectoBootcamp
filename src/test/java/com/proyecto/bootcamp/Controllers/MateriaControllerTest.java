package com.proyecto.bootcamp.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Services.MateriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

// @AutoConfigureMockMvc
@SpringBootTest
class MateriaControllerTest{
    /*@Autowired 
    MockMvc mockMvc;

    @MockBean
    MateriaService materiaService;

    MateriaDTOWithCurso dtoToTest = new MateriaDTOWithCurso();

    List<MateriaDTOWithCurso> listDTOs = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dtoToTest.setId(UUID.randomUUID());
        dtoToTest.setDia("lunes");
        dtoToTest.setHora(9);
        dtoToTest.setFechainicio(Date.valueOf("2022-01-01"));
        dtoToTest.setFechafin(Date.valueOf("2022-12-12"));
        dtoToTest.setCurso(UUID.randomUUID());

        addDtos();
    }

    @Test
    public void getMateriaById() throws Exception {
        when(materiaService.getById(any(UUID.class))).thenReturn(dtoToTest);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/materias/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertMateria(resultActions);
    }

    @Test
    public void putMateria() throws Exception {
        when(materiaService.update(any(MateriaDTOWithCurso.class))).thenReturn(dtoToTest);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertMateria(resultActions);
    }

    @Test
    public void postMateria() throws Exception {
        when(materiaService.saveMateria(any(MateriaDTOWithCurso.class))).thenReturn(dtoToTest);

        dtoToTest.setId(null);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertMateria(resultActions);
    }

    @Test
    public void deleteMateria() throws Exception {
        doNothing().when(materiaService).delete(any(MateriaDTOWithCurso.class));
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/materias")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void getAll() throws Exception {
        when(materiaService.getAllPaginated(anyInt(), anyInt())).thenReturn(listDTOs);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/materias")
                        .param("page", "1")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String response = resultActions.getResponse().getContentAsString();
        List<MateriaDTOWithCurso> materiaDTO = new ObjectMapper().readValue(response, new TypeReference<List<MateriaDTOWithCurso>>() {});
        materiaDTO.forEach(materia -> {
            assertAll(()->{
                assertEquals(materia.getId(), dtoToTest.getId());
                assertEquals(materia.getHora(), dtoToTest.getHora());
                assertEquals(materia.getDia(), dtoToTest.getDia());
                assertEquals(materia.getFechafin().getClass(), dtoToTest.getFechafin().getClass());
                assertEquals(materia.getFechainicio().getClass(), dtoToTest.getFechainicio().getClass());
                assertEquals(materia.getCurso(), dtoToTest.getCurso());
            });
        });
    }

    private void assertMateria(MvcResult resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        String response = resultActions.getResponse().getContentAsString();

        MateriaDTOWithCurso materiaDTO = new ObjectMapper().readValue(response, MateriaDTOWithCurso.class);
        assertAll(()->{
            assertEquals(materiaDTO.getId(), dtoToTest.getId());
            assertEquals(materiaDTO.getHora(), dtoToTest.getHora());
            assertEquals(materiaDTO.getDia(), dtoToTest.getDia());
            assertEquals(materiaDTO.getFechafin().getClass(), dtoToTest.getFechafin().getClass());
            assertEquals(materiaDTO.getFechainicio().getClass(), dtoToTest.getFechainicio().getClass());
            assertEquals(materiaDTO.getCurso(), dtoToTest.getCurso());

        });
    }

    private void addDtos() {
        listDTOs.add(dtoToTest);
        listDTOs.add(dtoToTest);
    } */
}