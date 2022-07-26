package com.proyecto.bootcamp.Services.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    public UsuarioDTO usuarioToDTO(Usuario usuario);
    public Usuario dtoToUsuario(UsuarioDTO usuario);
}
