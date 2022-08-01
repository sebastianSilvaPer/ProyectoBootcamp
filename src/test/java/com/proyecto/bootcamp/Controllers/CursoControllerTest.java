package com.proyecto.bootcamp.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;

import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest()
public class CursoControllerTest {
    /* @Autowired
    MockMvc mockMvc;

    @MockBean
    CursoServices cursoServices;

    CursoDTO dtoToTest = new CursoDTO();

    List<CursoDTO> cursosDTO = new ArrayList<>();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dtoToTest.setId(UUID.randomUUID());
        dtoToTest.setNombre("Test curso");
        dtoToTest.setDescripcion("test");
        addDtos();
    }

    @Test
    public void getCursoById() throws Exception {
        when(cursoServices.getById(any(UUID.class))).thenReturn(dtoToTest);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cursos/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertCurso(resultActions);
    }

    @Test
    public void putCurso() throws Exception {
        when(cursoServices.update(any(CursoDTO.class))).thenReturn(dtoToTest);

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/cursos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertCurso(resultActions);
    }

    @Test
    public void postCurso() throws Exception {
        when(cursoServices.saveCurso(any(CursoDTO.class))).thenReturn(dtoToTest);

        dtoToTest.setId(null);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/cursos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertCurso(resultActions);
    }

    @Test
    public void deleteCurso() throws Exception {
        doNothing().when(cursoServices).delete(any(CursoDTO.class));
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/cursos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void getAll() throws Exception {
        when(cursoServices.getAllPaginated(anyInt(), anyInt())).thenReturn(cursosDTO);
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/cursos")
                        .param("page", "1")
                        .param("size", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(dtoToTest));

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String response = resultActions.getResponse().getContentAsString();
        List<CursoDTO> cursoDTO = new ObjectMapper().readValue(response, new TypeReference<List<CursoDTO>>() {});
        cursoDTO.forEach(curso -> {
            assertAll(()->{
                assertEquals(curso.getId(), dtoToTest.getId());
                assertEquals(curso.getNombre(), dtoToTest.getNombre());
                assertEquals(curso.getDescripcion(), dtoToTest.getDescripcion());
            });
        });
    }

    private void assertCurso(MvcResult resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        String response = resultActions.getResponse().getContentAsString();

        CursoDTO cursoDTO = new ObjectMapper().readValue(response, CursoDTO.class);
        assertAll(()->{
            assertEquals(cursoDTO.getId(), dtoToTest.getId());
            assertEquals(cursoDTO.getNombre(), dtoToTest.getNombre());
            assertEquals(cursoDTO.getDescripcion(), dtoToTest.getDescripcion());
            assertEquals(cursoDTO.getMaterias(), dtoToTest.getMaterias());
        });
    }

    private void addDtos() {
        cursosDTO.add(new CursoDTO(dtoToTest.getId(), dtoToTest.getNombre(),dtoToTest.getDescripcion(),new ArrayList<>()));
        cursosDTO.add(new CursoDTO(dtoToTest.getId(), dtoToTest.getNombre(),dtoToTest.getDescripcion(),new ArrayList<>()));
    } */
}
    