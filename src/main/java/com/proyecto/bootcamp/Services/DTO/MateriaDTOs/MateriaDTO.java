package com.proyecto.bootcamp.Services.DTO.MateriaDTOs;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Delete;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;

public class MateriaDTO {
    @Null(groups = Create.class)
    @NotNull(groups = {Update.class,Delete.class})
    private UUID id;
    
    @NotBlank
    @Pattern(regexp = "[a-zA-Z]{5,9}$", message = "{materia.dia.pattern}")
    private String dia;
    
    @Min(7)
    @Max(18)
    private Integer hora;
    
    @NotNull
    @Future(message = "{materia.fechainicio.future}")
    private Date fechafin;
    
    @NotNull
    @PastOrPresent(message = "{materia.fechainicio.pastorpresent}")
    private Date fechainicio;

    public MateriaDTO() {
    }

    public MateriaDTO(UUID id, String dia, Integer hora, Date fechafin, Date fechainicio) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.fechafin = fechafin;
        this.fechainicio = fechainicio;
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
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MateriaDTO)) {
            return false;
        }
        MateriaDTO materiaDTO = (MateriaDTO) o;
        return Objects.equals(id, materiaDTO.id) && Objects.equals(dia, materiaDTO.dia) && Objects.equals(hora, materiaDTO.hora) && Objects.equals(fechafin, materiaDTO.fechafin) && Objects.equals(fechainicio, materiaDTO.fechainicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dia, hora, fechafin, fechainicio);
    }

    @Override
    public String toString() {
        return "Materia{" +
            " id='" + getId() + "'" +
            ", dia='" + getDia() + "'" +
            ", hora='" + getHora() + "'" +
            ", fechafin='" + getFechafin() + "'" +
            ", fechainicio='" + getFechainicio() + "'" +
            "}\n";
    }
        
}
 
