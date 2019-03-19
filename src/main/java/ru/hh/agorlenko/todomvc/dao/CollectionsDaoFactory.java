package ru.hh.agorlenko.todomvc.dao;

import ru.hh.agorlenko.todomvc.dao.impl.ToDoItemDaoImpl;

/**
 * Collections DAO
 */
public class CollectionsDaoFactory extends DaoFactory {

  private static final ToDoItemDaoImpl toDoItemDAO = new ToDoItemDaoImpl();

  public ToDoItemDao getToDoItemDao() {
    return toDoItemDAO;
  }

}
