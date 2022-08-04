package com.proyecto.bootcamp.Security.Errors;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.bootcamp.Exceptions.JsonResponse;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode("Access_Denied");
        jsonResponse.setMessage(accessDeniedException.getMessage());
        jsonResponse.setDescripction("Access Denied");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(new ObjectMapper().writeValueAsString(jsonResponse));
        out.flush();
    }
}
