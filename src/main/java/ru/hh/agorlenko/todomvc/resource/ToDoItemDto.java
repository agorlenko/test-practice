package ru.hh.agorlenko.todomvc.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ToDoItemDto {

  @JsonProperty(required = true)
  private long id;

  @JsonProperty(required = true)
  private String title;

  @JsonProperty(required = true)
  private boolean completed;

  public ToDoItemDto() {
  }

  public ToDoItemDto(long id, String title, boolean completed) {
    this.id = id;
    this.title = title;
    this.completed = completed;
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

    ToDoItemDto item = (ToDoItemDto) o;

    return id == item.id;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

}
