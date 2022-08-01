package com.proyecto.bootcamp.Services.DTO.CursoDTOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

public class CursoDTO {
    @Null(groups = {Create.class})
    @NotNull(groups = {Update.class})
    private UUID id;
    @NotBlank
    private String nombre;
    @NotNull
    private String descripcion;
    
    private List<MateriaDTO> materias = new ArrayList<>();

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MateriaDTO> getMaterias() {
        return this.materias;
    }

    public void setMaterias(List<MateriaDTO> materias) {
        this.materias = materias;
    }


    public CursoDTO() {
    }

    public CursoDTO(UUID id, String nombre, String descripcion, List<MateriaDTO> materias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.materias = materias;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CursoDTO)) {
            return false;
        }
        CursoDTO cursoDTO = (CursoDTO) o;
        return Objects.equals(id, cursoDTO.id) && Objects.equals(nombre, cursoDTO.nombre) && Objects.equals(descripcion, cursoDTO.descripcion) && Objects.equals(materias, cursoDTO.materias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, materias);
    }

    @Override
    public String toString() {
        return "Curso{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", materias='" + getMaterias() + "'" +
            "}\n";
    }

}
