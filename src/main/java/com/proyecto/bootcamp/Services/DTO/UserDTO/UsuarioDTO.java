package com.proyecto.bootcamp.Services.DTO.UserDTO;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;

public class UsuarioDTO {
    @Null(groups = Create.class)
    @NotNull(groups = {Update.class})
    private UUID id;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    @Email
    private String correo;
    @NotBlank
    @Size(min = 6, max = 30)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).$")
    private String clave;
    @NotBlank
    private String rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(UUID id, String nombre, String apellido, String correo, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
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

    public UsuarioDTO id(UUID id) {
        setId(id);
        return this;
    }

    public UsuarioDTO nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public UsuarioDTO apellido(String apellido) {
        setApellido(apellido);
        return this;
    }

    public UsuarioDTO correo(String correo) {
        setCorreo(correo);
        return this;
    }

    public UsuarioDTO clave(String clave) {
        setClave(clave);
        return this;
    }

    public UsuarioDTO rol(String rol) {
        setRol(rol);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioDTO)) {
            return false;
        }
        UsuarioDTO usuarioDTO = (UsuarioDTO) o;
        return Objects.equals(id, usuarioDTO.id) && Objects.equals(nombre, usuarioDTO.nombre) && Objects.equals(apellido, usuarioDTO.apellido) && Objects.equals(correo, usuarioDTO.correo) && Objects.equals(clave, usuarioDTO.clave) && Objects.equals(rol, usuarioDTO.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, correo, clave, rol);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", clave='" + getClave() + "'" +
            ", rol='" + getRol() + "'" +
            "}";
    }


}
