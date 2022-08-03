package com.proyecto.bootcamp.Services.Mapper;

import java.util.List;
import java.util.UUID;

import com.proyecto.bootcamp.DAO.Models.BasicEntity;
import com.proyecto.bootcamp.Services.DTO.BasicDTO;

public interface MapperGeneric<E extends BasicEntity<UUID>, D extends BasicDTO<UUID>> {
    E mapToEntity(D dto);
    D mapToDto(E entity);

    List<E> mapListToEntity(List<D> dtos);
    List<D> mapListToDto(List<E> entities);
}
