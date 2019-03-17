package ru.hh.agorlenko.todomvc.dao;

import ru.hh.agorlenko.todomvc.dao.impl.ToDoItemDAOImpl;
import ru.hh.agorlenko.todomvc.model.ToDoItem;

/**
 * Collections DAO
 */
public class CollectionsDAOFactory extends DAOFactory {

  private static final ToDoItemDAOImpl toDoItemDAO = new ToDoItemDAOImpl();

  public ToDoItemDAO getToDoItemDAO() {
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