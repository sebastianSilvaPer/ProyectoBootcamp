package com.proyecto.bootcamp.Services.Mapper.usuario;

import org.mapstruct.Mapper;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.MapperGeneric;

@Mapper(componentModel = "spring")
public interface UsuarioMongoMapper extends MapperGeneric<Usuario, UsuarioDTO> {

}
