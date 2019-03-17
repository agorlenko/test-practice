package ru.hh.agorlenko.todomvc.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.hh.agorlenko.todomvc.model.ToDoItem;
import ru.hh.agorlenko.todomvc.service.ToDoItemService;

@Path("/item")
@Produces(MediaType.APPLICATION_JSON)
public class ToDoItemResource {

  private static final ToDoItemService toDoItemService = new ToDoItemService();

  @GET
  @Path("/status")
  public Response getStatus() {
    return Response.status(Response.Status.OK).build();
  }

  @GET
  @Path("/{id}")
  public Response getItemById(@PathParam("id") long id) {
    final ToDoItem item = toDoItemService.getItemById(id);
    if (item == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(new ToDoItemDTO(item)).build();
  }

  @GET
  @Path("/all")
  public List<ToDoItemDTO> getAllItems() {
    return toDoItemService.getAllItems().stream()
            .map(ToDoItemDTO::new)
            .collect(Collectors.toList());
  }

  @POST
  @Path("/create")
  public Response createItem(ToDoItemDTO item) {
    toDoItemService.createItem(new ToDoItem(item));
    return Response.status(Response.Status.OK).build();
  }

  @PUT
  @Path("/{id}")
  public Response updateItem(@PathParam("id") long id, ToDoItemDTO item) {
    final int updateCount = toDoItemService.updateItem(id, new ToDoItem(item));
    if (updateCount == 1) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @DELETE
  @Path("/delete/{id}")
  public Response deleteItem(@PathParam("id") long id) {
    final int deleteCount = toDoItemService.deleteItem(id);
    if (deleteCount == 1) {
      return Response.status(Response.Status.OK).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @DELETE
  @Path("/drop")
  public Response deleteItems() {
    toDoItemService.deleteItems();
    return Response.status(Response.Status.OK).build();
  }

}
