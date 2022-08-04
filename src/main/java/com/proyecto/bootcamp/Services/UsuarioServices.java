package com.proyecto.bootcamp.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioCrudRepository;
import com.proyecto.bootcamp.Exceptions.UnAuthorizedException;
import com.proyecto.bootcamp.Security.Tokens.TokensUtils;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.usuario.UsuarioMapper;

@Service
public class UsuarioServices implements UserDetailsService,TokensUtils{
    @Autowired
    private UsuarioCrudRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioMapper mapper;

    @Autowired
    Algorithm algorithm;

    @Autowired
    TextEncryptor userEncryptor;

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioDTO usuario = getUsuarioByCorreo(username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));
        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getClave(), authorities);
    }

    public Map<String, String> refreshToken(String authorizationHeader, String URI){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);    
                String email = decodedJWT.getSubject();
                email = userEncryptor.decrypt(email);

                UsuarioDTO user = this.getUsuarioByCorreo(email);

                Collection<GrantedAuthority> authorities = new ArrayList<>();
                    
                authorities.add(new SimpleGrantedAuthority(user.getRol()));

                String accessToken = createAccessTokenJWT(user.getCorreo(), URI, authorities, algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                return tokens;
            } catch (Exception e) { 
                throw new UnAuthorizedException("Not authorized Token");
            }
        }else{
            throw new UnAuthorizedException("Refresh token missing");
        }
    }
}
