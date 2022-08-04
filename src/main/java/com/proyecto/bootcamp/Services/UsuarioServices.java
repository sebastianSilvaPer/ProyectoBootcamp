package com.proyecto.bootcamp.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioCrudRepository;
import com.proyecto.bootcamp.Exceptions.UniqueValueException;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.usuario.UsuarioMapper;

@Service
public class UsuarioServices implements UserDetailsService{
    @Autowired
    private UsuarioCrudRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioMapper mapper;

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        checkUsuarioByCorreo(usuarioDTO.getCorreo());
        Usuario usuarioEntity = mapper.mapToEntity(usuarioDTO);
        usuarioEntity.setClave(passwordEncoder.encode(usuarioEntity.getClave()));
        repository.save(usuarioEntity); 
        
        return mapper.mapToDto(usuarioEntity);
    }

    public UsuarioDTO getUsuarioByCorreo(String correo) {
        Optional<Usuario> usuario = repository.findByCorreo(correo);
        return mapper.mapToDto(usuario.get());
    }

    public List<UsuarioDTO> getUsuariosPaginated(int page, int size){
        List<UsuarioDTO> usuarios = StreamSupport.stream(repository.findAllPaginated(size, (page)*size).spliterator(), false)
                                            .map(mapper::mapToDto)
                                            .toList();
        return usuarios;      
    }

    public void checkUsuarioByCorreo(String correo) {
        if(repository.existByCorreo(correo))
            throw new UniqueValueException("Correo already exists");
    }

    //Username on details its the correo on the database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDTO usuario = getUsuarioByCorreo(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getClave(), authorities);
    }
}
