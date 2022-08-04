package com.proyecto.bootcamp.Controllers;

import java.util.List;
import java.util.Map;

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

import com.proyecto.bootcamp.Controllers.Constants.MessageConstants;
import com.proyecto.bootcamp.Services.TokenServices;
import com.proyecto.bootcamp.Services.UsuarioServices;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioServices usuarioServices;

    @Autowired
    TokenServices tokenServices;

    @GetMapping()
    public List<UsuarioDTO> getUsuariosPagination(@RequestParam(defaultValue = "0",required = false) @PositiveOrZero(message = MessageConstants.MESSAGE_PAGE_ZERO) Integer page,
                                                @RequestParam(defaultValue = "100",required = false) @Positive(message = MessageConstants.MESSAGE_SIZE_POSITIVE) Integer size){
        return usuarioServices.getUsuariosPaginated(page, size);    
    }

    @PostMapping()
    public UsuarioDTO postMateria(@RequestBody UsuarioDTO usuarioDTO){
       return usuarioServices.saveUsuario(usuarioDTO);
    }

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response){
        return tokenServices.refreshToken(request.getHeader("Authorization"), request.getRequestURI());
    }
}

