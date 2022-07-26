package com.proyecto.bootcamp.DAO.Models;

import java.util.Objects;
import java.util.UUID;

public class Usuario extends BasicEntity<UUID>{
    private String nombre;
    private String apellido;
    private String correo;
    private String clave;
    private String rol;

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Usuario apellido(String apellido) {
        setApellido(apellido);
        return this;
    }

    public Usuario correo(String correo) {
        setCorreo(correo);
        return this;
    }

    public Usuario clave(String clave) {
        setClave(clave);
        return this;
    }

    public Usuario rol(String rol) {
        setRol(rol);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) && Objects.equals(apellido, usuario.apellido) && Objects.equals(correo, usuario.correo) && Objects.equals(clave, usuario.clave) && Objects.equals(rol, usuario.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, correo, clave, rol);
    }

    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", clave='" + getClave() + "'" +
            ", rol='" + getRol() + "'" +
            "}";
    }

}
