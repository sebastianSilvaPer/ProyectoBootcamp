package com.proyecto.bootcamp.Services.Mapper;

import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-07-28T09:25:00-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public UsuarioDTO usuarioToDTO(Usuario usuario) {
        if ( usuario == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setApellido( usuario.getApellido() );
        usuarioDTO.setClave( usuario.getClave() );
        usuarioDTO.setCorreo( usuario.getCorreo() );
        usuarioDTO.setId( usuario.getId() );
        usuarioDTO.setNombre( usuario.getNombre() );
        usuarioDTO.setRol( usuario.getRol() );

        return usuarioDTO;
    }

    @Override
    public Usuario dtoToUsuario(UsuarioDTO usuario) {
        if ( usuario == null ) {
            return null;
        }

        Usuario usuario1 = new Usuario();

        usuario1.setId( usuario.getId() );
        usuario1.setApellido( usuario.getApellido() );
        usuario1.setClave( usuario.getClave() );
        usuario1.setCorreo( usuario.getCorreo() );
        usuario1.setNombre( usuario.getNombre() );
        usuario1.setRol( usuario.getRol() );

        return usuario1;
    }
}
