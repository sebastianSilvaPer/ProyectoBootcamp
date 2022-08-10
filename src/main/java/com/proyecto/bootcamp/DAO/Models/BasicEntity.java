package com.proyecto.bootcamp.DAO.Models;

import org.springframework.data.annotation.Id;

public class BasicEntity<ID> {
    @Id
    ID id;
    boolean borrado;

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                "}";
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
}
