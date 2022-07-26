package com.proyecto.bootcamp.Controllers;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Exceptions.UnAuthorizedException;
import com.proyecto.bootcamp.Services.UsuarioServices;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioServices services;

    @GetMapping()
    public List<UsuarioDTO> getUsuariosPagination(@RequestParam(defaultValue = "0",required = false) @PositiveOrZero(message = "{page.zero}") Integer page,
    @RequestParam(defaultValue = "100",required = false) @Positive(message = "{size.positive}") Integer size){
        return services.getUsuariosPaginated(page, size);    
    }

    @PostMapping()
    public UsuarioDTO postMateria(@RequestBody UsuarioDTO usuarioDTO){
       return services.saveUsuario(usuarioDTO);
    }

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);    
                String username = decodedJWT.getSubject();
                UsuarioDTO user = services.getUsuarioByCorreo(username);

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

