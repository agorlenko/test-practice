package ru.hh.agorlenko.todomvc.dao;

import ru.hh.agorlenko.todomvc.dao.impl.ToDoItemDaoImpl;
import ru.hh.agorlenko.todomvc.model.ToDoItem;

/**
 * Collections DAO
 */
public class CollectionsDaoFactory extends DaoFactory {

  private static final ToDoItemDaoImpl toDoItemDAO = new ToDoItemDaoImpl();

  public ToDoItemDao getToDoItemDao() {
    return toDoItemDAO;
  }

  @Override
  public void populateTestData() {
    ToDoItem item1 = new ToDoItem(1, "test1");
    ToDoItem item2 = new ToDoItem(2, "test2");
    ToDoItem item3 = new ToDoItem(3, "test3");
    toDoItemDAO.insertItem(item1);
    toDoItemDAO.insertItem(item2);
    toDoItemDAO.insertItem(item3);
  }

}