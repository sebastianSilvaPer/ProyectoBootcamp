package com.proyecto.bootcamp.DAO.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.bootcamp.DAO.Models.Curso;

@Repository
public interface CursoRepository extends BasicCrudRepository<Curso, UUID> {
    @Override
    @Query("Select * FROM CURSO WHERE id=:id AND borrado='f'")
    @Transactional(readOnly = true)
    Optional<Curso> findById(@Param("id") UUID id);

    @Override
    @Query("SELECT * FROM CURSO WHERE borrado='f' ")
    @Transactional(readOnly = true)
    Iterable<Curso> findAll();

    @Query("SELECT * FROM CURSO WHERE borrado='f' ORDER BY id DESC " +
            "LIMIT :limit OFFSET :offset")
    Iterable<Curso> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);

    @Override
    @Query("UPDATE CURSO set borrado='t' where id=:id")
    @Transactional
    @Modifying
    void deleteById(@Param("id") UUID id);

    @Override
    @Query("UPDATE CURSO set borrado='t'")
    @Transactional
    @Modifying
    void deleteAll();

    @Transactional
    @Modifying
    default void deleteAllList(List<Curso> entities){
        entities.forEach((curso)->this.deleteById(curso.getId()));
    };

    @Override
    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM CURSO c where c.id = :id AND c.borrado='f'")
    @Transactional(readOnly = true)
    boolean existsById(@Param("id") UUID id);

    @Override
    @Query("SELECT count(*) FROM CURSO WHERE borrado='f'")
    @Transactional(readOnly = true)
    long count();

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM CURSO c where LOWER(c.nombre) LIKE LOWER(:nombre) AND c.borrado='f'")
    @Transactional(readOnly = true)
    Boolean existByNombre(@Param("nombre") String nombre);
}
