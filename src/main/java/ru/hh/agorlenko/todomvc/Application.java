package ru.hh.agorlenko.todomvc;

import ru.hh.nab.starter.NabApplication;

public class Application {

  public static void main(String[] args) {
    NabApplication.builder()
            .configureJersey(ApplicationJerseyConfig.class).bindToRoot()
            .build().run(ApplicationConfig.class);
  }
}
