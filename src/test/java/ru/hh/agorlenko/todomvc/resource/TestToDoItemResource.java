package ru.hh.agorlenko.todomvc.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.hh.agorlenko.todomvc.ApplicationTestConfig;
import ru.hh.agorlenko.todomvc.ApplicationTestJerseyConfig;
import ru.hh.agorlenko.todomvc.dao.DaoFactory;
import ru.hh.nab.starter.NabApplication;
import ru.hh.nab.starter.server.jetty.JettyServer;

public class TestToDoItemResource {

  private static final DaoFactory daoFactory = DaoFactory.getDaoFactory(DaoFactory.Collections);

  private static JettyServer server;

  private static final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();

  private static HttpClient client;

  private final ObjectMapper mapper = new ObjectMapper();
  private final URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:5656");

  @BeforeClass
  public static void setup() {

    // server
    server = NabApplication.builder().configureJersey(ApplicationTestJerseyConfig.class).bindToRoot().build()
        .run(ApplicationTestConfig.class);

    // client
    connManager.setDefaultMaxPerRoute(100);
    connManager.setMaxTotal(200);
    client = HttpClients.custom().setConnectionManager(connManager).setConnectionManagerShared(true).build();
  }

  @Before
  public void populateTestData() {
    daoFactory.populateTestData();
  }


  @Test
  public void testStatus() throws IOException, URISyntaxException {
    URI uri = builder.setPath("/item/status").build();
    HttpGet request = new HttpGet(uri);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);
  }

  @Test
  public void testGetAllItems() throws IOException, URISyntaxException {
    URI uri = builder.setPath("/item/all").build();
    HttpGet request = new HttpGet(uri);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);
    String jsonString = EntityUtils.toString(response.getEntity());
    List<ToDoItemDto> items = mapper.readValue(jsonString,
        mapper.getTypeFactory().constructCollectionType(List.class, ToDoItemDto.class));
    assertTrue(items.size() > 0);
  }

  @Test
  public void testGetExistingItemById() throws IOException, URISyntaxException {
    long itemId = 1;
    URI uri = builder.setPath("/item/" + itemId).build();
    HttpGet request = new HttpGet(uri);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);
    String jsonString = EntityUtils.toString(response.getEntity());
    ToDoItemDto item = mapper.readValue(jsonString, ToDoItemDto.class);
    assertEquals(itemId, item.getId());
  }

  @Test
  public void testGetNonexistentItemById() throws IOException, URISyntaxException {
    long itemId = 123;
    URI uri = builder.setPath("/item/" + itemId).build();
    HttpGet request = new HttpGet(uri);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(404, statusCode);
  }

  @Test
  public void testCreateItem() throws IOException, URISyntaxException {

    long itemId = 4;

    // create new item
    URI uri = builder.setPath("/item/create").build();
    ToDoItemDto item = new ToDoItemDto(itemId, "test" + itemId, false);
    String jsonInString = mapper.writeValueAsString(item);
    StringEntity entity = new StringEntity(jsonInString);
    HttpPost request = new HttpPost(uri);
    request.setHeader("Content-type", "application/json");
    request.setEntity(entity);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);

    // get created item
    uri = builder.setPath("/item/" + itemId).build();
    HttpGet requestGet = new HttpGet(uri);
    HttpResponse responseGet = client.execute(requestGet);
    statusCode = responseGet.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);
    String jsonString = EntityUtils.toString(responseGet.getEntity());
    ToDoItemDto receivedItem = mapper.readValue(jsonString, ToDoItemDto.class);
    assertEquals(item, receivedItem);

  }

  @Test
  public void testUpdateExistingItem() throws IOException, URISyntaxException {

    long itemId = 2;

    // update item
    URI uri = builder.setPath("/item/" + itemId).build();
    ToDoItemDto item = new ToDoItemDto(itemId, "changed", false);
    String jsonInString = mapper.writeValueAsString(item);
    StringEntity entity = new StringEntity(jsonInString);
    HttpPut request = new HttpPut(uri);
    request.setHeader("Content-type", "application/json");
    request.setEntity(entity);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);

    // get updated item
    uri = builder.setPath("/item/" + itemId).build();
    HttpGet requestGet = new HttpGet(uri);
    HttpResponse responseGet = client.execute(requestGet);
    statusCode = responseGet.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);
    String jsonString = EntityUtils.toString(responseGet.getEntity());
    ToDoItemDto receivedItem = mapper.readValue(jsonString, ToDoItemDto.class);
    assertEquals(item, receivedItem);

  }

  @Test
  public void testUpdateNonexistentItem() throws IOException, URISyntaxException {

    long itemId = 22;

    // update item
    URI uri = builder.setPath("/item/" + itemId).build();
    ToDoItemDto item = new ToDoItemDto(itemId, "changed", false);
    String jsonInString = mapper.writeValueAsString(item);
    StringEntity entity = new StringEntity(jsonInString);
    HttpPut request = new HttpPut(uri);
    request.setHeader("Content-type", "application/json");
    request.setEntity(entity);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(404, statusCode);

  }

  @Test
  public void testDeleteExistingItem() throws IOException, URISyntaxException {

    long itemId = 3;

    URI uri = builder.setPath("/item/delete/" + itemId).build();
    HttpDelete request = new HttpDelete(uri);
    request.setHeader("Content-type", "application/json");
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);

    // get deleted item
    uri = builder.setPath("/item/" + itemId).build();
    HttpGet requestGet = new HttpGet(uri);
    HttpResponse responseGet = client.execute(requestGet);
    statusCode = responseGet.getStatusLine().getStatusCode();
    assertEquals(404, statusCode);

  }

  @Test
  public void testDeleteNonexistentItem() throws IOException, URISyntaxException {

    long itemId = 33;

    URI uri = builder.setPath("/item/delete/" + itemId).build();
    HttpDelete request = new HttpDelete(uri);
    request.setHeader("Content-type", "application/json");
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(404, statusCode);
  }

  @Test
  public void testDeleteAllItems() throws IOException, URISyntaxException {

    URI uri = builder.setPath("/item/drop").build();
    HttpDelete request = new HttpDelete(uri);
    HttpResponse response = client.execute(request);
    int statusCode = response.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);


    uri = builder.setPath("/item/all").build();
    HttpGet requestGetAll = new HttpGet(uri);
    HttpResponse responseGetAll = client.execute(requestGetAll);
    statusCode = responseGetAll.getStatusLine().getStatusCode();
    assertEquals(200, statusCode);
    String jsonString = EntityUtils.toString(responseGetAll.getEntity());
    List<ToDoItemDto> items = mapper.readValue(jsonString,
            mapper.getTypeFactory().constructCollectionType(List.class, ToDoItemDto.class));
    assertEquals(0, items.size());
  }

  @After
  public void dropTestData() {
    daoFactory.getToDoItemDao().dropItems();
  }

  @AfterClass
  public static void tearDown() {
    if (server.isRunning()) {
      server.stop();
    }
    HttpClientUtils.closeQuietly(client);
  }

}