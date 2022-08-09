package com.proyecto.bootcamp.Integration;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioRepository;

@SpringBootTest
public class TestingMongo {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void contextLoads() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setCorreo("sebas@gmail.com");
        usuario.setClave("123456");
        usuario.setRol("admin");
        
        usuarioRepository.save(usuario);
    }
}
