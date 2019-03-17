package ru.hh.agorlenko.todomvc.dao;

public abstract class DaoFactory {

  public static final int Collections = 1;

  public abstract ToDoItemDao getToDoItemDao();

  public static DaoFactory getDaoFactory(int factoryCode) {

    switch (factoryCode) {
      case Collections:
        return new CollectionsDaoFactory();
      default:
        // by default using collections
        return new CollectionsDaoFactory();
    }

  }

  public abstract void populateTestData();

}
