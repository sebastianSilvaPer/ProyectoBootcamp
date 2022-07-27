package com.proyecto.bootcamp.Security.Tokens;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class DefaultAlgorithmConfig {
    @Bean
	public Algorithm algorithm(){
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        return algorithm;
	}

    @Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
