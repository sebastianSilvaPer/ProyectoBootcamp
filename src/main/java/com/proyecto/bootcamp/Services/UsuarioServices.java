package com.proyecto.bootcamp.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioRepository;
import com.proyecto.bootcamp.Exceptions.NotFoundException;
import com.proyecto.bootcamp.Exceptions.UniqueValueException;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.usuario.UsuarioMapper;

@Service
public class UsuarioServices implements UserDetailsService {
    @Autowired
    UsuarioRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioMapper mapper;

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        checkUsuarioByCorreo(usuarioDTO.getCorreo());
        encodeClave(usuarioDTO);

        Usuario usuarioEntity = mapper.mapToEntity(usuarioDTO);
        usuarioEntity.setId(UUID.randomUUID());

        repository.save(usuarioEntity);

        return mapper.mapToDto(usuarioEntity);
    }

    public List<UsuarioDTO> getAll(int page, int size) {
        List<Usuario> findAll = repository.findAll(PageRequest.of(page, size)).getContent();
        return mapper.mapListToDto(findAll);
    }

    public UsuarioDTO updateUsuario(UsuarioDTO usuarioDTO, UUID id) {
        usuarioDTO.setId(id);
        checkUsuarioById(usuarioDTO.getId());
        encodeClave(usuarioDTO);

        Usuario usuarioEntity = mapper.mapToEntity(usuarioDTO);

        repository.update(usuarioEntity);
        return mapper.mapToDto(usuarioEntity);
    }

    public void deleteUsuario(UsuarioDTO usuario) {
        checkUsuarioById(usuario.getId());
        Usuario usuarioEntity = mapper.mapToEntity(usuario);
        repository.delete(usuarioEntity);
    }

    public UsuarioDTO getUsuarioByCorreo(String correo) {
        Optional<Usuario> usuario = repository.findByCorreo(correo);
        return mapper.mapToDto(usuario.get());
    }

    public List<UsuarioDTO> updateUsuarioList(List<UsuarioDTO> usuarioDTOs) {
        checkListById(usuarioDTOs);
        usuarioDTOs.forEach(this::encodeClave);

        List<Usuario> usuarioEntities = mapper.mapListToEntity(usuarioDTOs);
        Iterable<Usuario> updatedUsuarios = repository.update(usuarioEntities);
        List<Usuario> listUsuarios = iterableToList(updatedUsuarios);

        return mapper.mapListToDto(listUsuarios);
    }

    public void deleteUsuarioList(List<UsuarioDTO> usuarioDTOs) {
        checkListById(usuarioDTOs);
        List<Usuario> usuarioEntities = mapper.mapListToEntity(usuarioDTOs);
        repository.deleteAll(usuarioEntities);
    }

    public UsuarioDTO getUsuarioById(UUID id) {
        checkUsuarioById(id);
        Optional<Usuario> foundUsuarioOptional = repository.findById(id);
        return mapper.mapToDto(foundUsuarioOptional.get());
    }

    public void deleteUsuarioById(UUID id) {
        checkUsuarioById(id);
        repository.deleteById(id);
    }

    public UsuarioDTO encodeClave(UsuarioDTO usuarioDTO) {
        usuarioDTO.setClave(passwordEncoder.encode(usuarioDTO.getClave()));
        return usuarioDTO;
    }

    public void checkListById(List<UsuarioDTO> usuarioDTOs) {
        usuarioDTOs.forEach((Usuario) -> {
            checkUsuarioById(Usuario.getId());
        });
    }

    public void checkUsuarioById(UUID id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Usuario not exists");
    }

    public void checkUsuarioByCorreo(String correo) {
        if (repository.existsByCorreo(correo))
            throw new UniqueValueException("Correo already exists");
    }

    public List<Usuario> iterableToList(Iterable<Usuario> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .toList();
    }

    // Username on details its the "correo" on the database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDTO usuario = getUsuarioByCorreo(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getClave(),
                authorities);
    }
}
