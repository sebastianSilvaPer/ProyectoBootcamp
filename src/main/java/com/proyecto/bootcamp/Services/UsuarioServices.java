package com.proyecto.bootcamp.Services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.DAO.Models.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioCrudRepository;
import com.proyecto.bootcamp.Exceptions.UnAuthorizedException;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;
import com.proyecto.bootcamp.Services.Mapper.UsuarioMapper;

@Service
public class UsuarioServices implements UserDetailsService{
    @Autowired
    private UsuarioCrudRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioMapper mapper;

    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuarioEntity = mapper.dtoToUsuario(usuarioDTO);
        usuarioEntity.setClave(passwordEncoder.encode(usuarioEntity.getClave()));
        repository.save(usuarioEntity); 
        
        return mapper.usuarioToDTO(usuarioEntity);
    }

    public UsuarioDTO getUsuarioByCorreo(String correo) {
        Optional<Usuario> usuario = repository.findByCorreo(correo);
        return mapper.usuarioToDTO(usuario.get());
    }

    public List<UsuarioDTO> getUsuariosPaginated(int page, int size){
        List<UsuarioDTO> usuarios = StreamSupport.stream(repository.findAllPaginated(size, (page)*size).spliterator(), false)
                                            .map(mapper::usuarioToDTO)
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

    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);    
                String username = decodedJWT.getSubject();
                UsuarioDTO user = this.getUsuarioByCorreo(username);

                String accessToken = JWT.create()
                    .withSubject(user.getCorreo())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURI().toString())
                    .withClaim("roles", Arrays.asList(user.getRol()))
                    .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON.toString());//application/json
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) { 
                throw new UnAuthorizedException("Not authorized Token");
            }
            
        }else{
            throw new UnAuthorizedException("Refresh token missing");
        }
    }
}
