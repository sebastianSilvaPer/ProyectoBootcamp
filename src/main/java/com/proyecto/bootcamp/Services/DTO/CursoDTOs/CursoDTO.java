package com.proyecto.bootcamp.Services.DTO.CursoDTOs;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.proyecto.bootcamp.Services.DTO.BasicDTO;

public class CursoDTO extends BasicDTO<UUID> {
    @NotBlank(message = "{cursoDTO.NotBlank.nombre}")
    private String nombre;
    @NotNull(message = "{cursoDTO.NotNull.descripcion}")
    private String descripcion;

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
        return Objects.equals(this.getId(), cursoDTO.getId()) && Objects.equals(nombre, cursoDTO.nombre)
                && Objects.equals(descripcion, cursoDTO.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), nombre, descripcion);
    }

    @Override
    public String toString() {
        return "Curso{" +
                " id='" + getId() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                "}\n";
    }
}
