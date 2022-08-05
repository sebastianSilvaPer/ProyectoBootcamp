package com.proyecto.bootcamp.Security.Filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Security.Tokens.TokensUtils;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements TokensUtils{
    private final AuthenticationManager authenticationManager;

    private final Algorithm algorithm;

    private final TextEncryptor userEncryptor;
    
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, Algorithm algorithm, TextEncryptor userEncryptor) {
        this.authenticationManager = authenticationManager;
        this.algorithm = algorithm;
        this.userEncryptor = userEncryptor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String email = user.getUsername();
        
        String accessToken = createAccessTokenJWT(email, request.getRequestURI().toString(), user.getAuthorities(), algorithm, userEncryptor);
        String refreshToken = createRefreshTokenJWT(email, request.getRequestURI().toString(), algorithm, userEncryptor);
        
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
