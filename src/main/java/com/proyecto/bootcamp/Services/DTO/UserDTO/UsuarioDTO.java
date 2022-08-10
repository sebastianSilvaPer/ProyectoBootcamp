package com.proyecto.bootcamp.Services.DTO.UserDTO;

import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.proyecto.bootcamp.Services.DTO.BasicDTO;

public class UsuarioDTO extends BasicDTO<UUID> {
    @NotBlank(message = "usuario.NotBlank.nombre")
    private String nombre;
    @NotBlank(message = "usuario.NotBlank.apellido")
    private String apellido;
    @NotBlank(message = "usuario.NotBlank.correo")
    @Email(message = "usuario.email.correo")
    private String correo;
    @NotBlank(message = "usuario.NotBlank.clave")
    @Size(min = 6, max = 30, message = "usuario.clave.size")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "{usuario.clave.pattern}")
    private String clave;
    @NotBlank(message = "usuario.NotBlank.rol")
    private String rol;

    public UsuarioDTO() {
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
        return Objects.equals(this.getId(), usuarioDTO.getId()) && Objects.equals(nombre, usuarioDTO.nombre)
                && Objects.equals(apellido, usuarioDTO.apellido) && Objects.equals(correo, usuarioDTO.correo)
                && Objects.equals(clave, usuarioDTO.clave) && Objects.equals(rol, usuarioDTO.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), nombre, apellido, correo, clave, rol);
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
