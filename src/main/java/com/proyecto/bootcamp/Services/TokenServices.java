package com.proyecto.bootcamp.Services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.proyecto.bootcamp.Exceptions.UnAuthorizedException;
import com.proyecto.bootcamp.Security.Tokens.TokensUtils;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@Service
public class TokenServices implements TokensUtils {
    @Autowired
    UsuarioServices usuarioServices;

    @Autowired
    Algorithm algorithm;

    @Autowired
    TextEncryptor userEncryptor;
    
    public Map<String, String> refreshToken(String authorizationHeader, String URI){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);    
                String email = decodedJWT.getSubject();
                email = userEncryptor.decrypt(email);

                UsuarioDTO user = usuarioServices.getUsuarioByCorreo(email);

                Collection<GrantedAuthority> authorities = new ArrayList<>();
                    
                authorities.add(new SimpleGrantedAuthority(user.getRol()));

                String accessToken = createAccessTokenJWT(user.getCorreo(), URI, authorities, algorithm, userEncryptor);

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
