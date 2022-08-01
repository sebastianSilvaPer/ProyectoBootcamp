package com.proyecto.bootcamp.Security.Tokens;

import java.sql.Date;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public interface TokensUtils {
    public default String createAccessTokenJWT(String name, String url, Collection<GrantedAuthority> authorities, Algorithm algorithm){
        int accessTime = 10 * 60 * 1000;
    
        // Collection<GrantedAuthority> authorities;
        String accessToken = JWT.create()
                .withSubject(name)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTime))
                .withIssuer(url)
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
        return accessToken;   
    };

    public default String createRefreshTokenJWT(String name, String url, Algorithm algorithm){
        int refreshTime = 30 * 60 * 1000;

        String refreshToken = JWT.create()
                .withSubject(name)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTime))
                .withIssuer(url)
                .sign(algorithm);
        return refreshToken;
    };
}
