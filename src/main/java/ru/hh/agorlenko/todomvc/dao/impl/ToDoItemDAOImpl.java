package ru.hh.agorlenko.todomvc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.hh.agorlenko.todomvc.dao.ToDoItemDAO;
import ru.hh.agorlenko.todomvc.model.ToDoItem;

public class ToDoItemDAOImpl implements ToDoItemDAO {

  private static final ConcurrentMap<Long, ToDoItem> itemIdMap = new ConcurrentHashMap<>();

  @Override
  public ToDoItem getItemById(long id) {
    return itemIdMap.get(id);
  }

  @Override
  public List<ToDoItem> getAllItems() {
    return new ArrayList<>(itemIdMap.values());
  }

  @Override
  public void insertItem(ToDoItem item) {
    itemIdMap.put(item.getId(), item);
  }

  @Override
  public int updateItem(long id, ToDoItem item) {
    ToDoItem oldItem = getItemById(id);
    if (oldItem == null) {
      return 0;
    }
    oldItem.setTitle(item.getTitle());
    oldItem.setCompleted(item.getCompleted());
    return 1;
  }

  @Override
  public int deleteItem(long id) {
    ToDoItem oldItem = getItemById(id);
    if (oldItem == null) {
      return 0;
    }
    itemIdMap.remove(id);
    return 1;
  }

  @Override
  public void dropItems() {
    itemIdMap.clear();
  }

}
