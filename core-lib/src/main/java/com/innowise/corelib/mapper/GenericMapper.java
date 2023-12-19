package com.innowise.corelib.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface GenericMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    void edit(E entity, @MappingTarget E editableEntity);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);
}
