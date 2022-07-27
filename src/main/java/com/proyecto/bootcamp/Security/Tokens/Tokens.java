package com.proyecto.bootcamp.Security.Tokens;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public interface Tokens {
    public default String createAccessTokenJWT(String name, String url, Collection<GrantedAuthority> authorities, Algorithm algorithm){
        int accessTime = 1 * 60 * 1000;
    
        String accessToken = JWT.create()
                .withSubject(name)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTime))
                .withIssuer(url)
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
        return accessToken;   
    };

    public default String createRefreshTokenJWT(String name, String url, Algorithm algorithm){
        int refreshTime = 1 * 60 * 1000;

        String refreshToken = JWT.create()
                .withSubject(name)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTime))
                .withIssuer(url)
                .sign(algorithm);
        return refreshToken;
    };
}
