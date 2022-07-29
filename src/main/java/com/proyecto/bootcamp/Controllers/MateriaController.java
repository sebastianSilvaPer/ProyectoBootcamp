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

import com.proyecto.bootcamp.Controllers.Constants.MessageConstants;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Delete;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;
import com.proyecto.bootcamp.Services.MateriaService;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTOWithCurso;

@RestController
@RequestMapping("/cursos/{idCurso}/materias")
@Validated
public class MateriaController {
    @Autowired
    MateriaService materiaService;

    @GetMapping("/{id}")
    public MateriaDTOWithCurso getMateriaById(@PathVariable("id") UUID id) {
        return materiaService.getById(id);
    }

    @GetMapping()
    public List<MateriaDTO> getMaterias(@PathVariable("idCurso") UUID cursoId){            
        return materiaService.findAllByCursoId(cursoId);    
    }

    @PostMapping()
    public MateriaDTOWithCurso postMateria(@Validated(value = Create.class) @RequestBody MateriaDTOWithCurso materiaDTO){
       return materiaService.saveMateria(materiaDTO);
    }

    @PutMapping()
    public MateriaDTOWithCurso putMateria(@Validated(value = Update.class) @RequestBody MateriaDTOWithCurso materiaDTO) {
        return materiaService.update(materiaDTO);
    }

    @DeleteMapping()
    public void deleteMateria(@Validated(value = Delete.class) @RequestBody MateriaDTOWithCurso materiaDTO) {
        materiaService.delete(materiaDTO);
    }
}
