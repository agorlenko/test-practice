package ru.hh.agorlenko.todomvc.resource;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.agorlenko.todomvc.ApplicationTestConfig;
import ru.hh.agorlenko.todomvc.dao.DaoFactory;
import ru.hh.agorlenko.todomvc.model.ToDoItem;
import ru.hh.nab.starter.NabApplication;
import ru.hh.nab.testbase.NabTestBase;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@ContextConfiguration(classes = ApplicationTestConfig.class)
public class TestToDoItemResource extends NabTestBase {

  private static final DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.Collections);

  @Before
  public void populateTestData() {
    ToDoItem item1 = new ToDoItem(1, "test1");
    ToDoItem item2 = new ToDoItem(2, "test2");
    ToDoItem item3 = new ToDoItem(3, "test3");
    daoFactory.getToDoItemDao().insertItem(item1);
    daoFactory.getToDoItemDao().insertItem(item2);
    daoFactory.getToDoItemDao().insertItem(item3);
  }

  @After
  public void dropTestData() {
    daoFactory.getToDoItemDao().dropItems();
  }

  @Test
  public void testStatus() {
    Response response = target("/item/status").request().get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void testGetAllItems() {
    Response response = target("/item/all").request().get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    assertFalse(response.readEntity(new GenericType<List<ToDoItemDto>>() { }).isEmpty());
  }

  @Test
  public void testGetExistingItemById() {
    long itemId = 1;
    Response response = target("/item/" + itemId).request().get();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    ToDoItemDto item = response.readEntity(ToDoItemDto.class);
    assertEquals(itemId, item.getId());
  }

  @Test
  public void testGetNonexistentItemById() {
    long itemId = 123;
    Response response = target("/item/" + itemId).request().get();
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
  }

  @Test
  public void testCreateItem() {

    long itemId = 4;

    // create item
    ToDoItemDto item = new ToDoItemDto(itemId, "test" + itemId, false);
    Response response = target("/item").path("create")
            .request().post(Entity.entity(item, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    // get created item
    Response responseGet = target("/item/" + itemId).request().get();
    assertEquals(Response.Status.OK.getStatusCode(), responseGet.getStatus());
    ToDoItemDto receivedItem = responseGet.readEntity(ToDoItemDto.class);
    assertEquals(item, receivedItem);

  }

  @Test
  public void testUpdateExistingItem() {

    Long itemId = 2L;

    // update item
    ToDoItemDto item = new ToDoItemDto(itemId, "changed", false);
    Response response = target("/item").path(itemId.toString())
            .request().put(Entity.entity(item, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    // get updated item
    Response responseGet = target("/item/" + itemId).request().get();
    assertEquals(Response.Status.OK.getStatusCode(), responseGet.getStatus());
    ToDoItemDto receivedItem = responseGet.readEntity(ToDoItemDto.class);
    assertEquals(item, receivedItem);

  }

  @Test
  public void testUpdateNonexistentItem() {

    Long itemId = 22L;

    // update item
    ToDoItemDto item = new ToDoItemDto(itemId, "changed", false);
    Response response = target("/item").path(itemId.toString())
            .request().put(Entity.entity(item, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

  }

  @Test
  public void testDeleteExistingItem() {

    Long itemId = 3L;

    Response response = target("/item").path("delete").path(itemId.toString()).request().delete();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    // get deleted item
    Response responseGet = target("/item/" + itemId).request().get();
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), responseGet.getStatus());

  }

  @Test
  public void testDeleteNonexistentItem() {

    Long itemId = 33L;

    Response response = target("/item").path("delete").path(itemId.toString()).request().delete();
    assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

  }

  @Test
  public void testDeleteAllItems() {

    Response response = target("/item").path("drop").request().delete();
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    Response responseGetAll = target("/item/all").request().get();
    assertEquals(Response.Status.OK.getStatusCode(), responseGetAll.getStatus());
    assertEquals(0, responseGetAll.readEntity(new GenericType<List<ToDoItemDto>>() { }).size());

  }

  @Override
  protected NabApplication getApplication() {
    return NabApplication.builder().configureJersey().registerResources(ToDoItemResource.class).bindToRoot().build();
  }

}