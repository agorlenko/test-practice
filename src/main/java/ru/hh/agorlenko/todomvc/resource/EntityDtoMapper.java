package ru.hh.agorlenko.todomvc.resource;

public interface EntityDtoMapper<E, D> {

  E toEntity(D dto);

  D toDto(E entity);

}
