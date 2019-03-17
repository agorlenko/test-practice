package ru.hh.agorlenko.todomvc.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.agorlenko.todomvc.model.ToDoItem;

public class ToDoItemDTO {

  @JsonProperty(required = true)
  private long id;

  @JsonProperty(required = true)
  private String title;

  @JsonProperty(required = true)
  private boolean completed;

  public ToDoItemDTO() {
  }

  public ToDoItemDTO(long id, String title, boolean completed) {
    this.id = id;
    this.title = title;
    this.completed = completed;
  }

  public ToDoItemDTO(ToDoItem item) {
    this.id = item.getId();
    this.title = item.getTitle();
    this.completed = item.getCompleted();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

    ToDoItemDTO item = (ToDoItemDTO) o;

    return id == item.id;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

}
