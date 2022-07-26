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
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.UsuarioMapper;

@Service
public class UsuarioServices implements UserDetailsService{
    @Autowired
    private UsuarioCrudRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    UsuarioMapper mapper = UsuarioMapper.INSTANCE;

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioEntity = mapper.dtoToUsuario(usuarioDTO);
        usuarioEntity.setClave(passwordEncoder.encode(usuarioEntity.getClave()));
        repository.save(usuarioEntity); 
        
        return mapper.usuarioToDTO(usuarioEntity);
    }

    public Usuario getUsuarioByCorreo(String correo) {
        Optional<Usuario> usuario = repository.findByCorreo(correo);
        return usuario.get();
    }

    public List<UsuarioDTO> getUsuariosPaginated(int page, int size){
        List<UsuarioDTO> usuarios = StreamSupport.stream(repository.findAllPaginated(size, (page)*size).spliterator(), false)
                                            .map(mapper::usuarioToDTO)
                                            .toList();
        return usuarios;      
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = getUsuarioByCorreo(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getClave(), authorities);
    }
}
