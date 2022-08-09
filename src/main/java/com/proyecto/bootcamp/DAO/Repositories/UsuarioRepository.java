package com.proyecto.bootcamp.DAO.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.bootcamp.DAO.Document.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, UUID>{
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByCorreo(String correo);
}
    
