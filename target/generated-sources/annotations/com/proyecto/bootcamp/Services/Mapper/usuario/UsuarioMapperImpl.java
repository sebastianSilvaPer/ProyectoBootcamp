package com.proyecto.bootcamp.Services.Mapper.usuario;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-09T23:48:45-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public List<UsuarioDTO> mapListToDto(List<Usuario> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( entities.size() );
        for ( Usuario usuario : entities ) {
            list.add( mapToDto( usuario ) );
        }

        return list;
    }

    @Override
    public List<Usuario> mapListToEntity(List<UsuarioDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Usuario> list = new ArrayList<Usuario>( dtos.size() );
        for ( UsuarioDTO usuarioDTO : dtos ) {
            list.add( mapToEntity( usuarioDTO ) );
        }

        return list;
    }

    @Override
    public UsuarioDTO mapToDto(Usuario entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.id( entity.getId() );
        usuarioDTO.setApellido( entity.getApellido() );
        usuarioDTO.setClave( entity.getClave() );
        usuarioDTO.setCorreo( entity.getCorreo() );
        usuarioDTO.setNombre( entity.getNombre() );
        usuarioDTO.setRol( entity.getRol() );

        return usuarioDTO;
    }

    @Override
    public Usuario mapToEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setApellido( dto.getApellido() );
        usuario.setClave( dto.getClave() );
        usuario.setCorreo( dto.getCorreo() );
        usuario.setId( dto.getId() );
        usuario.setNombre( dto.getNombre() );
        usuario.setRol( dto.getRol() );

        return usuario;
    }
}
