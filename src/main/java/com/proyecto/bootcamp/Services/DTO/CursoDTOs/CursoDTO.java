package com.proyecto.bootcamp.Services.DTO.CursoDTOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.proyecto.bootcamp.Services.DTO.BasicDTO;
import com.proyecto.bootcamp.Services.DTO.MateriaDTOs.MateriaDTO;

public class CursoDTO extends BasicDTO<UUID> {
    @NotBlank
    private String nombre;
    @NotNull
    private String descripcion;
    
    private List<MateriaDTO> materias = new ArrayList<>();


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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CursoDTO)) {
            return false;
        }
        CursoDTO cursoDTO = (CursoDTO) o;
        return Objects.equals(this.getId(), cursoDTO.getId()) && Objects.equals(nombre, cursoDTO.nombre) && Objects.equals(descripcion, cursoDTO.descripcion) && Objects.equals(materias, cursoDTO.materias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), nombre, descripcion, materias);
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
