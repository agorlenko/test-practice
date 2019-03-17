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

public class TestToDoItemDao {

  private static final DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.Collections);

  @Before
  public void populateTestData() {
    // prepare test data
    daoFactory.populateTestData();
  }

  @Test
  public void getDefaultDao() {
    assertEquals(daoFactory.getClass(), DaoFactory.getDaoFactory(-1).getClass());
  }

  @Test
  public void testGetAllItems() {
    List<ToDoItem> allItems = daoFactory.getToDoItemDao().getAllItems();
    assertTrue(allItems.size() > 0);
  }

  @Test
  public void testGetExistingItemById() {
    long itemId = 1;
    ToDoItem item = daoFactory.getToDoItemDao().getItemById(itemId);
    assertEquals(itemId, item.getId());
  }

  @Test
  public void testGetNonexistentItemById() {
    long itemId = 12;
    ToDoItem item = daoFactory.getToDoItemDao().getItemById(itemId);
    assertNull(item);
  }

  @Test
  public void testInsertItem() {
    long itemId = 4;
    ToDoItem item = new ToDoItem(itemId, "test4");
    daoFactory.getToDoItemDao().insertItem(item);
    assertEquals(item, daoFactory.getToDoItemDao().getItemById(itemId));
  }

  @Test
  public void testUpdateExistingItem() {
    long itemId = 1;
    ToDoItem item = new ToDoItem(itemId,"changed");
    item.setCompleted(true);
    ToDoItem existingItem = daoFactory.getToDoItemDao().getItemById(item.getId());
    assertNotEquals(item, existingItem);
    int updatedCount = daoFactory.getToDoItemDao().updateItem(item.getId(), item);
    assertEquals(1, updatedCount);
    ToDoItem updatedItem = daoFactory.getToDoItemDao().getItemById(item.getId());
    assertEquals(item, updatedItem);
  }

  @Test
  public void testUpdateNonexistentItem() {
    long itemId = 123;
    ToDoItem item = new ToDoItem(itemId, "test123");
    assertEquals(0, daoFactory.getToDoItemDao().updateItem(itemId, item));
  }

  @Test
  public void testDeleteExistingItem() {
    long itemId = 1;
    assertEquals(1, daoFactory.getToDoItemDao().deleteItem(itemId));
    assertNull(daoFactory.getToDoItemDao().getItemById(itemId));
  }

  @Test
  public void testDeleteNonexistentItem() {
    long itemId = 123;
    assertEquals(0, daoFactory.getToDoItemDao().deleteItem(itemId));
  }

  @After
  public void dropTestData() {
    daoFactory.getToDoItemDao().dropItems();
  }

}