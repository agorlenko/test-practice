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
  private static final ToDoItemMapper mapper = new ToDoItemMapper();

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
    return Response.ok(mapper.toDto(item)).build();
  }

  @GET
  @Path("/all")
  public List<ToDoItemDto> getAllItems() {
    return toDoItemService.getAllItems().stream()
            .map(mapper::toDto)
            .collect(Collectors.toList());
  }

  @POST
  @Path("/create")
  public Response createItem(ToDoItemDto item) {
    toDoItemService.createItem(mapper.toEntity(item));
    return Response.status(Response.Status.OK).build();
  }

  @PUT
  @Path("/{id}")
  public Response updateItem(@PathParam("id") long id, ToDoItemDto item) {
    final int updateCount = toDoItemService.updateItem(id, mapper.toEntity(item));
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
