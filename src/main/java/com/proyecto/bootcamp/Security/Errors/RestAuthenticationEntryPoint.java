package com.proyecto.bootcamp.Security.Errors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Exceptions.JsonResponse;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode("Authorization_Failed");
        jsonResponse.setMessage("The authorization has failed"); 
        jsonResponse.setDescripction(authException.getMessage());
        
        
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(new ObjectMapper().writeValueAsString(jsonResponse));
        out.flush();
    }

}
