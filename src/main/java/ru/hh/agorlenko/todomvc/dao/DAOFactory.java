package ru.hh.agorlenko.todomvc.dao;

public abstract class DAOFactory {

  public static final int Collections = 1;

  public abstract ToDoItemDAO getToDoItemDAO();

  public static DAOFactory getDAOFactory(int factoryCode) {

    switch (factoryCode) {
      case Collections:
        return new CollectionsDAOFactory();
      default:
        // by default using collections
        return new CollectionsDAOFactory();
    }

  }

  public abstract void populateTestData();

}
