package com.proyecto.bootcamp.Services.Mapper.usuario;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-10T06:20:48-0500",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 1.4.100.v20220318-0906, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario mapToEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( dto.getId() );
        usuario.setNombre( dto.getNombre() );
        usuario.setApellido( dto.getApellido() );
        usuario.setCorreo( dto.getCorreo() );
        usuario.setClave( dto.getClave() );
        usuario.setRol( dto.getRol() );

        return usuario;
    }

    @Override
    public UsuarioDTO mapToDto(Usuario entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.id( entity.getId() );
        usuarioDTO.nombre( entity.getNombre() );
        usuarioDTO.apellido( entity.getApellido() );
        usuarioDTO.correo( entity.getCorreo() );
        usuarioDTO.clave( entity.getClave() );
        usuarioDTO.rol( entity.getRol() );

        return usuarioDTO;
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
}
