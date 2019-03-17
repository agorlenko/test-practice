package ru.hh.agorlenko.todomvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.agorlenko.todomvc.resource.ToDoItemResource;

@Configuration
@Import({ToDoItemResource.class})
public class ApplicationJerseyConfig {
}
