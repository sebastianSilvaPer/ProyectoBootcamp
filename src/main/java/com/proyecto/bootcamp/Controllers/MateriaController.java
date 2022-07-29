package com.proyecto.bootcamp.Controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Services.MateriaService;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

@RestController
@RequestMapping("/cursos/{idCurso}/materias")
@Validated
public class MateriaController {
    @Autowired
    MateriaService materiaService;

    //Create
    @PostMapping()
    public MateriaDTO postMateria(@PathVariable("idCurso") UUID cursoId, 
                                    @Validated(value = Create.class) @RequestBody MateriaDTO materiaDTO){
       return materiaService.saveMateria(materiaDTO, cursoId);
    }

    //Read
    @GetMapping()
    public List<MateriaDTO> getMaterias(@PathVariable("idCurso") UUID cursoId){            
        return materiaService.findAllByCursoId(cursoId);    
    }

    //Update
    @PutMapping()
    public List<MateriaDTO> putMateria(@PathVariable("idCurso") UUID cursoId, 
                                        @Validated @RequestBody List<MateriaDTO> materiaDTO) {
        return materiaService.updateList(materiaDTO, cursoId);
    }

    //Delete
    @DeleteMapping()
    public void delete(@PathVariable("idCurso") UUID cursoId){
        materiaService.deleteAllCursoId(cursoId);
    }

    //Id
    //Read
    @GetMapping("/{id}")
    public MateriaDTO getMateriaById(@PathVariable("id") UUID id) {
        return materiaService.getById(id);
    }
    
    //Update
    @PutMapping("/{id}")
    public MateriaDTO putMateriaById(@PathVariable("idCurso") UUID cursoId,
                                    @PathVariable("id") UUID id,
                                    @Validated @RequestBody MateriaDTO materiaDTO) {
        return materiaService.updateMateria(materiaDTO, id, cursoId);
    }
    
    //Delete
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable("id") UUID id){
        materiaService.deleteById(id);
    }
}
