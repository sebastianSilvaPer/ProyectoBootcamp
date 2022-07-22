package com.proyecto.bootcamp.DAO.Models;

import java.sql.Date;
import java.util.UUID;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("materia")
public class Materia extends BasicEntity<UUID>{
    private String dia;
    private Integer hora;
    @Column(value = "fecha_fin")
    private Date fechafin;
    @Column(value = "fecha_inicio")
    private Date fechainicio;
    @Column(value = "curso_id")
    private UUID curso;


    public UUID getCurso() {
        return this.curso;
    }

    public void setCurso(UUID curso) {
        this.curso = curso;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDia() {
        return this.dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getHora() {
        return this.hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Date getFechafin() {
        return this.fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Date getFechainicio() {
        return this.fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }


    @Override
    public String toString() {
        return "Materia{" +
            " id='" + getId() + "'" +
            ", dia='" + getDia() + "'" +
            ", hora='" + getHora() + "'" +
            ", fechafin='" + getFechafin() + "'" +
            ", fechainicio='" + getFechainicio() + "'" +
            ", curso='" + this.curso + "'" +
            "}\n";
    }
}
