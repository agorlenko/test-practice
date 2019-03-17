package ru.hh.agorlenko.todomvc.resource;

import ru.hh.agorlenko.todomvc.model.ToDoItem;

public class ToDoItemMapper implements EntityDtoMapper<ToDoItem, ToDoItemDto> {
  @Override
  public ToDoItem toEntity(ToDoItemDto dto) {
    ToDoItem entity = new ToDoItem(dto.getId(), dto.getTitle());
    entity.setCompleted(dto.getCompleted());
    return entity;
  }

  @Override
  public ToDoItemDto toDto(ToDoItem entity) {
    return new ToDoItemDto(entity.getId(), entity.getTitle(), entity.getCompleted());
  }
}
