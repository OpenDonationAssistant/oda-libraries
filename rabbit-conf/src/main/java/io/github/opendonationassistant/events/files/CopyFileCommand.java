package io.github.opendonationassistant.events.files;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class CopyFileCommand implements FilesCommand {

  private final MinioCoordinates source;
  private final MinioCoordinates destination;

  public CopyFileCommand(
    MinioCoordinates source,
    MinioCoordinates destination
  ) {
    this.source = source;
    this.destination = destination;
  }

  @Serdeable
  public static record MinioCoordinates(String bucketName, String fileName) {}
}
