package io.github.opendonationassistant.events.files;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CreateBucketCommand implements FilesCommand {
  private final String name;

  public CreateBucketCommand(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
