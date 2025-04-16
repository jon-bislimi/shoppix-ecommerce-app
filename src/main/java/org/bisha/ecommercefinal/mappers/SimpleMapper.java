package org.bisha.ecommercefinal.mappers;

import org.mapstruct.Mapper;

import java.util.List;

public interface SimpleMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntityList(List<D> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    default List<D> toDtoList(List<E> entities) {
        return entities.stream().map(this::toDto).toList();
    }
}
