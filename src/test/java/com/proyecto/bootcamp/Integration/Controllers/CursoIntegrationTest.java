package com.proyecto.bootcamp.Integration.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.UnsupportedEncodingException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Integration.IT.PostgresContainerTest;
import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
public class CursoIntegrationTest extends PostgresContainerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CursoServices cursoServices;

    CursoDTO dtoToTest = new CursoDTO();

    List<CursoDTO> cursosDTO = new ArrayList<>();

    @BeforeEach
    public void setup() {
        dtoToTest.setNombre("Test curso");
        dtoToTest.setDescripcion("test");
        addDtos();
    }

    @Test
    public void postCurso_ReturnOkAndAssertCurso_True() throws Exception {
        dtoToTest.setNombre(UUID.randomUUID().toString());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/cursos")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(dtoToTest));

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertCurso(resultActions);
    }

    @Test
    public void getAll_ReturnOkAndAssertSize_True() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/cursos")
                .param("page", "1")
                .param("size", "3")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String response = resultActions.getResponse().getContentAsString();
        List<CursoDTO> cursoDTOList = new ObjectMapper().readValue(response, new TypeReference<List<CursoDTO>>() {
        });

        assertAll(() -> {
            cursoDTOList.forEach(curso -> {
                assertTrue(curso instanceof CursoDTO);
            });
            assertTrue(cursoDTOList.size() == 3);
        });
    }

    @Test
    public void putCurso_ReturnOkAndNotEqualName_True() throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/cursos")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(cursosDTO));

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void getCursoById_ReturnSameCurso_True() throws Exception {
        CursoDTO cursoDTO = cursoServices.getAllPaginated(1, 1).get(0);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/cursos/" + cursoDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String response = resultActions.getResponse().getContentAsString();
        CursoDTO responseDto = new ObjectMapper().readValue(response, CursoDTO.class);

        assertAll(() -> {
            assertEquals(cursoDTO.getId(), responseDto.getId());
            assertEquals(cursoDTO.getNombre(), responseDto.getNombre());
            assertEquals(cursoDTO.getDescripcion(), responseDto.getDescripcion());
        });
    }

    @Test
    public void updateCursoById_ReturnOkAndAssertNotEqualCursoSameId_True() throws Exception {
        CursoDTO cursoDTO = cursoServices.getAllPaginated(1, 1).get(0);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/cursos/" + cursoDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(dtoToTest));

        MvcResult resultActions = this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String response = resultActions.getResponse().getContentAsString();
        CursoDTO responseDto = new ObjectMapper().readValue(response, CursoDTO.class);

        assertAll(() -> {
            assertEquals(cursoDTO.getId(), responseDto.getId());
            assertNotEquals(cursoDTO.getNombre(), responseDto.getNombre());
            assertNotEquals(cursoDTO.getDescripcion(), responseDto.getDescripcion());
        });
    }

    @Test
    public void deleteCursoById_ThrowsNotFoundAfterDelete_True() throws Exception {
        CursoDTO cursoDTO = cursoServices.getAllPaginated(1, 1).get(0);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/cursos/" + cursoDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        assertThrows(NotFoundException.class, () -> cursoServices.getById(cursoDTO.getId()));
    }

    private void assertCurso(MvcResult resultActions) throws UnsupportedEncodingException, JsonProcessingException {
        String response = resultActions.getResponse().getContentAsString();

        CursoDTO cursoDTO = new ObjectMapper().readValue(response, CursoDTO.class);
        assertAll(() -> {
            assertEquals(cursoDTO.getNombre(), dtoToTest.getNombre());
            assertEquals(cursoDTO.getDescripcion(), dtoToTest.getDescripcion());
        });
    }

    private void addDtos() {
        cursosDTO.add(dtoToTest);
        cursosDTO.add(dtoToTest);
    }
}
