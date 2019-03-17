package ru.hh.agorlenko.todomvc.service;

import java.util.List;

import ru.hh.agorlenko.todomvc.dao.DaoFactory;
import ru.hh.agorlenko.todomvc.model.ToDoItem;

public class ToDoItemService {

  private final DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.Collections);

  public ToDoItem getItemById(long id) {
    return daoFactory.getToDoItemDao().getItemById(id);
  }

  public List<ToDoItem> getAllItems() {
    return daoFactory.getToDoItemDao().getAllItems();
  }

  public void createItem(ToDoItem item) {
    daoFactory.getToDoItemDao().insertItem(item);
  }

  public int updateItem(long id, ToDoItem item) {
    return daoFactory.getToDoItemDao().updateItem(id, item);
  }

  public int deleteItem(long id) {
    return daoFactory.getToDoItemDao().deleteItem(id);
  }

  public void deleteItems() {
    daoFactory.getToDoItemDao().dropItems();
  }

}