package com.proyecto.bootcamp.Controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.bootcamp.Controllers.Constants.UtilConstants;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Create;
import com.proyecto.bootcamp.Exceptions.ValidationGroups.Update;
import com.proyecto.bootcamp.Services.TokenServices;
import com.proyecto.bootcamp.Services.UsuarioServices;
import com.proyecto.bootcamp.Services.DTO.UserDTO.UsuarioDTO;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {
    @Autowired
    UsuarioServices usuarioServices;

    @Autowired
    TokenServices tokenServices;

    @PostMapping()
    public UsuarioDTO postUsuario(@Validated(value = Create.class) @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServices.saveUsuario(usuarioDTO);
    }

    @GetMapping()
    public List<UsuarioDTO> getUsuariosPagination(
            @RequestParam(defaultValue = UtilConstants.DEFAULT_PAGE, required = false) @PositiveOrZero(message = UtilConstants.MESSAGE_PAGE_ZERO) Integer page,
            @RequestParam(defaultValue = UtilConstants.DEFAULT_SIZE, required = false) @Positive(message = UtilConstants.MESSAGE_SIZE_POSITIVE) Integer size) {
        return usuarioServices.getAll(page, size);
    }

    @PutMapping()
    public List<UsuarioDTO> putUsuarioList(
            @Validated(value = Update.class) @RequestBody List<UsuarioDTO> usuariosList) {
        return usuarioServices.updateUsuarioList(usuariosList);
    }

    @DeleteMapping()
    public void deleteUsuarioList(@RequestBody List<UsuarioDTO> usuariosList) {
        usuarioServices.deleteUsuarioList(usuariosList);
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuario(@PathVariable UUID id) {
        return usuarioServices.getUsuarioById(id);
    }

    @PutMapping("/{id}")
    public UsuarioDTO putUsuario(@PathVariable UUID id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioServices.updateUsuario(usuarioDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable UUID id) {
        usuarioServices.deleteUsuarioById(id);
    }
}
