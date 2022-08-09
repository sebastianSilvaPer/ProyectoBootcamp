package com.proyecto.bootcamp;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyecto.bootcamp.DAO.Document.Usuario;
import com.proyecto.bootcamp.DAO.Repositories.UsuarioRepository;
import com.proyecto.bootcamp.Services.Mapper.usuario.UsuarioMongoMapper;

@SpringBootApplication
public class BootcampApplication {
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioMongoMapper usuarioMongoMapper;
	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}

	@Bean
	public void test() {
		Usuario usuario = new Usuario();
		usuario.setId(UUID.fromString("10030de2-341f-40d3-bc3a-cd9bc2390743"));
        usuario.setNombre("Juan capo");
        usuario.setApellido("Perez");
        usuario.setCorreo("sebas@gmail.com");
        usuario.setClave("123456");
        usuario.setRol("admin");
        
        usuarioRepository.save(usuario);
		usuarioRepository.deleteById(UUID.fromString("ffa4dd7c-875c-433e-a80e-b1f1d7d0ab37"));

		Optional<Usuario> usuarioFound = usuarioRepository.findById(UUID.fromString("10030de2-341f-40d3-bc3a-cd9bc2390743"));

		System.out.println(usuarioMongoMapper.mapToDto(usuarioFound.get()));
	}
}
