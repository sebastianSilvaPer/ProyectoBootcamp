package com.proyecto.bootcamp.Services.DTO.MateriaDTOs;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import com.proyecto.bootcamp.Services.DTO.BasicDTO;

public class MateriaDTO extends BasicDTO<UUID>{
    @NotBlank(message = "{materia.NotBlank.dia}")
    @Pattern(regexp = "[a-zA-Z]{5,9}$", message = "{materia.dia.pattern}")
    private String dia;
    
    @Min(7)
    @Max(18)
    private Integer hora;
    
    @NotNull(message = "{materia.NotNull.fechainicio}")
    @Future(message = "{materia.fechafin.future}")
    private Date fechafin;
    
    @NotNull(message = "{materia.NotNull.fechafin}")
    @PastOrPresent(message = "{materia.fechainicio.pastorpresent}")
    private Date fechainicio;

    public MateriaDTO() {
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
        return Objects.equals(this.getId(), materiaDTO.getId()) && Objects.equals(dia, materiaDTO.dia) && Objects.equals(hora, materiaDTO.hora) && Objects.equals(fechafin, materiaDTO.fechafin) && Objects.equals(fechainicio, materiaDTO.fechainicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), dia, hora, fechafin, fechainicio);
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
 
