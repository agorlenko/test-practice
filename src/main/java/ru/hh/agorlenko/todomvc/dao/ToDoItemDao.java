package ru.hh.agorlenko.todomvc.dao;

import java.util.List;

import ru.hh.agorlenko.todomvc.model.ToDoItem;

public interface ToDoItemDao {

  ToDoItem getItemById(long id);

  List<ToDoItem> getAllItems();

  void insertItem(ToDoItem item);

  int updateItem(long id, ToDoItem item);

  int deleteItem(long id);

  void dropItems();

}
