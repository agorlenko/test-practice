package ru.hh.agorlenko.todomvc.service;

import java.util.List;

import ru.hh.agorlenko.todomvc.dao.DAOFactory;
import ru.hh.agorlenko.todomvc.model.ToDoItem;

public class ToDoItemService {

  private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.Collections);

  public ToDoItem getItemById(long id) {
    return daoFactory.getToDoItemDAO().getItemById(id);
  }

  public List<ToDoItem> getAllItems() {
    return daoFactory.getToDoItemDAO().getAllItems();
  }

  public void createItem(ToDoItem item) {
    daoFactory.getToDoItemDAO().insertItem(item);
  }

  public int updateItem(long id, ToDoItem item) {
    return daoFactory.getToDoItemDAO().updateItem(id, item);
  }

  public int deleteItem(long id) {
    return daoFactory.getToDoItemDAO().deleteItem(id);
  }

  public void deleteItems() {
    daoFactory.getToDoItemDAO().dropItems();
  }

}