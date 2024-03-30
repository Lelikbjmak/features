package com.innowise.datastarter.mapper;

import java.util.List;
import org.mapstruct.MappingTarget;

public interface GenericMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);

    void edit(E entity, @MappingTarget E editableEntity);

    List<D> toDtoList(List<E> entityList);

    List<E> toEntityList(List<D> dtoList);
}
