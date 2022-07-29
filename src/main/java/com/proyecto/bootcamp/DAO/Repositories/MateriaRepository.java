package com.proyecto.bootcamp.DAO.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.DAO.Models.Materia;

@Repository
public interface MateriaRepository extends BasicCrudRepository<Materia, UUID>{

    @Override
    @Query("Select * FROM MATERIA WHERE id=:id AND borrado='f'")
    @Transactional(readOnly = true)
    public Optional<Materia> findById(@Param("id") UUID id);

    @Override
    @Query("SELECT * FROM MATERIA WHERE borrado='f' ")
    @Transactional(readOnly = true)
    public Iterable<Materia> findAll();

    @Query("SELECT * FROM MATERIA WHERE borrado='f' ORDER BY id DESC "+
    "LIMIT :limit OFFSET :offset")
    public Iterable<Materia> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);

    @Override
    @Query("UPDATE MATERIA set borrado='t' where id=:id")
    @Transactional
    @Modifying
    public void deleteById(@Param("id") UUID id);

    @Override
    @Query("UPDATE MATERIA set borrado='t'")
    @Transactional
    @Modifying
	void deleteAll();

    @Override
    @Query("SELECT CASE WHEN count(m) > 0 THEN true ELSE false END FROM MATERIA m where m.id = :id AND m.borrado='f'")
    @Transactional(readOnly = true)
    public boolean existsById(@Param("id") UUID id);

    @Override
    @Query("SELECT count(*) FROM MATERIA WHERE borrado='f'")
    @Transactional(readOnly = true)
    public long count();

    @Query(" SELECT * FROM MATERIA WHERE curso_id = :curso_id AND borrado='f' ")
    @Transactional(readOnly = true)
    public Iterable<Materia> findAllByCursoId(@Param("curso_id") UUID cursoId);

    @Transactional
    default Iterable<Materia> findAllByCurso(Curso curso){
        return findAllByCursoId(curso.getId());
    };

    @Query("UPDATE MATERIA set borrado='t' WHERE curso_id = :curso_id")
    @Transactional
    @Modifying
	void deleteAllByCursoId(@Param("curso_id") UUID cursoId);
}
