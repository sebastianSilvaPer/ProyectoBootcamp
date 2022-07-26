package com.proyecto.bootcamp.DAO.Models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("curso")
public class Curso extends BasicEntity<UUID>{
    private String nombre;
    private String descripcion;
    @Column("curso_id")
    private Set<Materia> materias = new HashSet<>();


    public Curso(UUID id, String nombre, String descripcion, Set<Materia> materias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        materias.forEach(this::addMateria);
    }


    public Curso() {}

    public Curso(String nombre, String descripcion ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void addMateria(Materia materia) {
        materias.add(materia);
    }

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

    public Set<Materia> getMaterias() {
        return this.materias;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String toString() {
        return "Curso {" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}\n";
    }

}   