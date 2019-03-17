package ru.hh.agorlenko.todomvc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.hh.agorlenko.todomvc.model.ToDoItem;

public class TestToDoItemDAO {

  private static final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.Collections);

  @Before
  public void populateTestData() {
    // prepare test data
    daoFactory.populateTestData();
  }

  @Test
  public void getDefaultDAO() {
    assertEquals(daoFactory.getClass(), DAOFactory.getDAOFactory(-1).getClass());
  }

  @Test
  public void testGetAllItems() {
    List<ToDoItem> allItems = daoFactory.getToDoItemDAO().getAllItems();
    assertTrue(allItems.size() > 0);
  }

  @Test
  public void testGetExistingItemById() {
    long itemId = 1;
    ToDoItem item = daoFactory.getToDoItemDAO().getItemById(itemId);
    assertEquals(itemId, item.getId());
  }

  @Test
  public void testGetNonexistentItemById() {
    long itemId = 12;
    ToDoItem item = daoFactory.getToDoItemDAO().getItemById(itemId);
    assertNull(item);
  }

  @Test
  public void testInsertItem() {
    long itemId = 4;
    ToDoItem item = new ToDoItem(itemId, "test4");
    daoFactory.getToDoItemDAO().insertItem(item);
    assertEquals(item, daoFactory.getToDoItemDAO().getItemById(itemId));
  }

  @Test
  public void testUpdateExistingItem() {
    long itemId = 1;
    ToDoItem item = new ToDoItem(itemId,"changed");
    item.setCompleted(true);
    ToDoItem existingItem = daoFactory.getToDoItemDAO().getItemById(item.getId());
    assertNotEquals(item, existingItem);
    int updatedCount = daoFactory.getToDoItemDAO().updateItem(item.getId(), item);
    assertEquals(1, updatedCount);
    ToDoItem updatedItem = daoFactory.getToDoItemDAO().getItemById(item.getId());
    assertEquals(item, updatedItem);
  }

  @Test
  public void testUpdateNonexistentItem() {
    long itemId = 123;
    ToDoItem item = new ToDoItem(itemId, "test123");
    assertEquals(0, daoFactory.getToDoItemDAO().updateItem(itemId, item));
  }

  @Test
  public void testDeleteExistingItem() {
    long itemId = 1;
    assertEquals(1, daoFactory.getToDoItemDAO().deleteItem(itemId));
    assertNull(daoFactory.getToDoItemDAO().getItemById(itemId));
  }

  @Test
  public void testDeleteNonexistentItem() {
    long itemId = 123;
    assertEquals(0, daoFactory.getToDoItemDAO().deleteItem(itemId));
  }

  @After
  public void dropTestData() {
    daoFactory.getToDoItemDAO().dropItems();
  }

}