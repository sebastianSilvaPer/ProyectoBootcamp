package com.proyecto.bootcamp.Services.Mapper;

import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-26T09:05:32-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioDTO usuarioToDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.id( usuario.getId() );
        usuarioDTO.nombre( usuario.getNombre() );
        usuarioDTO.apellido( usuario.getApellido() );
        usuarioDTO.correo( usuario.getCorreo() );
        usuarioDTO.clave( usuario.getClave() );
        usuarioDTO.rol( usuario.getRol() );

        return usuarioDTO;
    }

    @Override
    public Usuario dtoToUsuario(UsuarioDTO usuario) {
        if ( usuario == null ) {
            return null;
        }

        Usuario usuario1 = new Usuario();

        usuario1.setId( usuario.getId() );
        usuario1.nombre( usuario.getNombre() );
        usuario1.apellido( usuario.getApellido() );
        usuario1.correo( usuario.getCorreo() );
        usuario1.clave( usuario.getClave() );
        usuario1.rol( usuario.getRol() );

        return usuario1;
    }
}
