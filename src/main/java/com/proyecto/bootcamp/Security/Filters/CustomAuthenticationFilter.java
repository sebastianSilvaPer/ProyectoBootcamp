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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Security.Tokens.Tokens;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements Tokens{
    private final AuthenticationManager authenticationManager;

    private final Algorithm algorithm;
    
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, Algorithm algorithm){
        this.authenticationManager = authenticationManager;
        this.algorithm = algorithm;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        String accessToken = createAccessTokenJWT(user.getUsername(), request.getRequestURI().toString(), user.getAuthorities(), algorithm);
        String refreshToken = createRefreshTokenJWT(user.getUsername(), request.getRequestURI().toString(), algorithm);
        
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
