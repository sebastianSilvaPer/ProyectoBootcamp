package com.proyecto.bootcamp.Services.Mapper;

import org.mapstruct.Mapper;

import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    public UsuarioDTO usuarioToDTO(Usuario usuario);
    public Usuario dtoToUsuario(UsuarioDTO usuario);
}
