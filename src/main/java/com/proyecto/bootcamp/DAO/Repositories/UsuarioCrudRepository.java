package com.proyecto.bootcamp.DAO.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proyecto.bootcamp.DAO.Models.Usuario;

@Repository
public interface UsuarioCrudRepository extends BasicCrudRepository<Usuario, UUID>{
    Optional<Usuario> findByCorreo(String correo);

    @Query("SELECT * FROM USUARIO ORDER BY id DESC "+
            "LIMIT :limit OFFSET :offset")
    public Iterable<Usuario> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);
}