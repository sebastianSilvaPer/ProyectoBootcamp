package com.proyecto.bootcamp.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioRepository;
import com.proyecto.bootcamp.Exceptions.UniqueValueException;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.usuario.UsuarioMapper;

@Service
public class UsuarioServices implements UserDetailsService{
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioMapper mapper;

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        checkUsuarioByCorreo(usuarioDTO.getCorreo());
        Usuario usuarioEntity = mapper.mapToEntity(usuarioDTO);

        usuarioEntity.setId(UUID.randomUUID());
        usuarioEntity.setClave(passwordEncoder.encode(usuarioEntity.getClave()));
        
        repository.save(usuarioEntity); 
        
        return mapper.mapToDto(usuarioEntity);
    }

    public List<UsuarioDTO> getAll(int page, int size) {
        List<Usuario> findAll = repository.findAll(PageRequest.of(page, size)).getContent();
        return mapper.mapListToDto(findAll);
    }

    public UsuarioDTO getUsuarioByCorreo(String correo) {
        Optional<Usuario> usuario = repository.findByCorreo(correo);
        return mapper.mapToDto(usuario.get());
    }

    public void checkUsuarioByCorreo(String correo) {
        if(repository.existsByCorreo(correo))
            throw new UniqueValueException("Correo already exists");
    }

    //Username on details its the "correo" on the database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDTO usuario = getUsuarioByCorreo(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getClave(), authorities);
    }
}
