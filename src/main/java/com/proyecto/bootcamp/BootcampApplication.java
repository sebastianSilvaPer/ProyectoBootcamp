package com.proyecto.bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.algorithms.Algorithm;

@SpringBootApplication
public class BootcampApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}
	
}
