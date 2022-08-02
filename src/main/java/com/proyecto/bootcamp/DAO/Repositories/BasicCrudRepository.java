package com.proyecto.bootcamp.DAO.Repositories;

import java.io.Serializable;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.bootcamp.DAO.Models.BasicEntity;

@NoRepositoryBean
public interface BasicCrudRepository<T extends BasicEntity<ID>, ID extends Serializable> extends CrudRepository<T, ID>{
    
    @Transactional(readOnly = true)
    public Iterable<T> findAllPaginated(@Param("limit") int limit, @Param("offset") int offset);

    @Transactional
    default public T update(T entity){
        return this.save(entity);
    };

    @Override
    @Transactional
    default void delete(T entity){
        deleteById(entity.getId());
    };

    @Override
    @Transactional
    default void deleteAllById(Iterable<? extends ID> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    @Transactional
	default void deleteAll(Iterable<? extends T> entities){
        entities.forEach(this::delete);
    };
}
