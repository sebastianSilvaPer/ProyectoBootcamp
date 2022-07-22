package com.proyecto.bootcamp.Services.DTO.MateriaDTOs;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

public class MateriaDTOWithCurso extends MateriaDTO{
    @NotNull
    UUID curso;

    public MateriaDTOWithCurso() {
    }

    public MateriaDTOWithCurso(UUID id, String dia, Integer hora, Date fechafin, Date fechainicio, UUID curso) {
        super.setId(id);
        super.setDia(dia);
        super.setHora(hora);
        super.setFechafin(fechafin);
        super.setFechainicio(fechainicio);
        this.curso = curso;
    }

    public void setCurso(UUID curso) {
        this.curso = curso;
    }

    public UUID getCurso() {
        return this.curso;
    }

    @Override
    public String toString() {
        return super.toString()+"{" +
            " curso='" + getCurso() + "'" +
            "}";
    }
}
