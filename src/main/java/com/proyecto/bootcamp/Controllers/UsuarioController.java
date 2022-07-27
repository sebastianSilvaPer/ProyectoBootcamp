package com.proyecto.bootcamp.Controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.bootcamp.Services.UsuarioServices;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioServices services;

    @GetMapping()
    public List<UsuarioDTO> getUsuariosPagination(@RequestParam(defaultValue = "0",required = false) @PositiveOrZero(message = "{page.zero}") Integer page,
    @RequestParam(defaultValue = "100",required = false) @Positive(message = "{size.positive}") Integer size){
        return services.getUsuariosPaginated(page, size);    
    }

    @PostMapping()
    public UsuarioDTO postMateria(@RequestBody UsuarioDTO usuarioDTO){
       return services.saveUsuario(usuarioDTO);
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response){
        services.refreshToken(request, response);
    }
}

