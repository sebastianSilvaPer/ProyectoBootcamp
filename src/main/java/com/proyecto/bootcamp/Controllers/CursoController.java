package com.proyecto.bootcamp.Controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.bootcamp.Controllers.Constants.UtilConstants;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;
import com.proyecto.bootcamp.Services.CursoServices;
import com.proyecto.bootcamp.Services.DTO.CursoDTOs.CursoDTO;

@RestController
@RequestMapping("/cursos")
@Validated
public class CursoController {
    @Autowired
    CursoServices cursoServices;

    @PostMapping()
    public CursoDTO postCurso(@Validated(value = Create.class) @RequestBody CursoDTO cursoDTO) {
        return cursoServices.saveCurso(cursoDTO);
    }

    @GetMapping
    public List<CursoDTO> getAll(
        @RequestParam(defaultValue = UtilConstants.DEFAULT_PAGE, required = false) @PositiveOrZero(message = UtilConstants.MESSAGE_PAGE_ZERO) Integer page,
        @RequestParam(defaultValue = UtilConstants.DEFAULT_SIZE, required = false) @Positive(message = UtilConstants.MESSAGE_SIZE_POSITIVE) Integer size) {
        return cursoServices.getAllPaginated(page, size);
    }

    @PutMapping()
    public CursoDTO putCurso(@Validated(value = Update.class) @RequestBody CursoDTO cursoDTO) {
        return cursoServices.update(cursoDTO);
    }

    @DeleteMapping()
    public void deleteAllCursos() {
        cursoServices.deleteAll();
    }

    @GetMapping("/{id}")
    public CursoDTO getCursoById(@PathVariable UUID id) {
        return cursoServices.getById(id);
    }

    @PutMapping("/{id}")
    public CursoDTO updateCursoById(@PathVariable UUID id, @Validated @RequestBody CursoDTO cursoDTO) {
        cursoDTO.setId(id);
        return cursoServices.update(cursoDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCursoById(@PathVariable UUID id) {
        cursoServices.deleteById(id);
    }
}
