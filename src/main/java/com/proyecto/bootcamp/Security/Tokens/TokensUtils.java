package com.proyecto.bootcamp.Security.Tokens;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public interface TokensUtils {
    public default String createAccessTokenJWT(String email, String url, Collection<GrantedAuthority> authorities, Algorithm algorithm, TextEncryptor userEncryptor){
        int accessTime = 10 * 60 * 1000;
    
        email = userEncryptor.encrypt(email);

        List<SimpleGrantedAuthority> authoritiesEncoded = authorities.stream()
                    .map(authority -> userEncryptor.encrypt(authority.getAuthority()))
                    .map(encoded -> new SimpleGrantedAuthority(encoded))
                    .collect(Collectors.toList());

        String accessToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTime))
                .withIssuer(url)
                .withClaim("roles", authoritiesEncoded.stream().map(GrantedAuthority::getAuthority).toList())
                .sign(algorithm);
        return accessToken;   
    };

    public default String createRefreshTokenJWT(String email, String url, Algorithm algorithm, TextEncryptor userEncryptor){
        int refreshTime = 30 * 60 * 1000;

        email = userEncryptor.encrypt(email);

        String refreshToken = JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTime))
                .withIssuer(url)
                .sign(algorithm);
        return refreshToken;
    };
}
