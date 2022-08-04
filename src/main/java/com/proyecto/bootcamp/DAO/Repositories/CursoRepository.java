package com.proyecto.bootcamp.DAO.Repositories;

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
    //Read methods
    @Override
    @Query("Select * FROM CURSO WHERE id=:id AND borrado='f'")
    @Transactional(readOnly = true)
    public Optional<Curso> findById(@Param("id") UUID id);

    @Override
    @Query("SELECT * FROM CURSO WHERE borrado='f' ")
    @Transactional(readOnly = true)
    public Iterable<Curso> findAll();

    @Query("SELECT * FROM CURSO WHERE borrado='f' ORDER BY id DESC "+
            "LIMIT :limit OFFSET :offset")
    public Iterable<Curso> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);

    @Override
    @Query("UPDATE CURSO set borrado='t' where id=:id")
    @Transactional
    @Modifying
    public void deleteById(@Param("id") UUID id);

    @Override
    @Query("UPDATE CURSO set borrado='t'")
    @Transactional
    @Modifying
    void deleteAll();

    @Override
    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM CURSO c where c.id = :id AND c.borrado='f'")
    @Transactional(readOnly = true)
    public boolean existsById(@Param("id") UUID id);

    @Override
    @Query("SELECT count(*) FROM CURSO WHERE borrado='f'")
    @Transactional(readOnly = true)
    public long count();

    @Query("SELECT CASE WHEN count(c) > 0 THEN true ELSE false END FROM CURSO c where LOWER(c.nombre) LIKE LOWER(:nombre) AND c.borrado='f'")
    @Transactional(readOnly = true)
    public Boolean existByNombre(@Param("nombre") String nombre); 
}
