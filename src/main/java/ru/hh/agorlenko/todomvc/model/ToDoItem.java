package ru.hh.agorlenko.todomvc.model;

import ru.hh.agorlenko.todomvc.resource.ToDoItemDTO;

public class ToDoItem {

  private long id;

  private String title;

  private boolean completed;

  public ToDoItem() {
  }

  public ToDoItem(long id, String title) {
    this.id = id;
    this.title = title;
  }

  public ToDoItem(ToDoItemDTO itemDTO) {
    this.id = itemDTO.getId();
    this.title = itemDTO.getTitle();
    this.completed = itemDTO.getCompleted();
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean getCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ToDoItem item = (ToDoItem) o;

    if (id != item.id) {
      return false;
    } else if (!title.equals(item.title)) {
      return false;
    }
    return completed == item.completed;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

}
