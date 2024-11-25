package io.github.opendonationassistant.events.command;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Command {

  private String id;
  private String command;

  public Command(String id, String command) {
    this.id = id;
    this.command = command;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }
}
