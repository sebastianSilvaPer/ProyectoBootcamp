package com.proyecto.bootcamp.Controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.bootcamp.Services.TokenServices;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Autowired
    TokenServices tokenServices;

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return tokenServices.refreshToken(request.getHeader("Authorization"), request.getRequestURI());
    }
}
