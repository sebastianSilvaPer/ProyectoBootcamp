package com.proyecto.bootcamp.Security.Filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.proyecto.bootcamp.Exceptions.UnAuthorizedException;

@Configurable
public class CustomAuthorizationFilter extends OncePerRequestFilter{
    // @Autowired
    private Algorithm algorithm;
    private TextEncryptor userEncryptor;

    public CustomAuthorizationFilter(Algorithm algorithm, TextEncryptor userEncryptor) {
        this.algorithm = algorithm;
        this.userEncryptor = userEncryptor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/usuarios/refresh")){
            filterChain.doFilter(request, response);
        } else{
            String authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try {
                    String token = authorizationHeader.substring("Bearer ".length());

                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(token);    

                    String username = decodedJWT.getSubject();
                    List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                    roles.forEach(rol -> {
                        String decrypt = userEncryptor.decrypt(rol.toString());
                        authorities.add(new SimpleGrantedAuthority(decrypt));
                    });
                    
                    System.out.println();
                    
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); 
                    filterChain.doFilter(request, response);
                } catch (Exception e) { 
                    throw new UnAuthorizedException("Not authorized Token");
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }   
    }
}
