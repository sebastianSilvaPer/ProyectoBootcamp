package com.proyecto.bootcamp.initConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioRepository;

@Configuration
public class initConfigUser {
    @Autowired
	UsuarioRepository usuarioRepository;

    @Bean
	public void initUser() {
		Usuario usuario = new Usuario();

		usuario.setNombre("Juan");
		usuario.setApellido("Perez");
		usuario.setClave("$2a$12$IvJl4f11rHpbw.xkoCINhO0gsidOVVtF6ZRe5Z.4Pkto/ZTgDVUz2");
		usuario.setCorreo("juan@gmail.com");
		usuario.setRol("ROL_ADMIN");

		usuarioRepository.save(usuario);
	}
}
