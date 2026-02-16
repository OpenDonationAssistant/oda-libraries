package io.github.opendonationassistant.events.files;

import io.micronaut.serde.annotation.Serdeable;

public interface FilesCommand {
  @Serdeable
  public static record CreateBucketCommand(String name) {}

  @Serdeable
  public static record CopyFileCommand(
    MinioCoordinates source,
    MinioCoordinates destination
  ) {}

  @Serdeable
  public static record MinioCoordinates(String bucketName, String fileName) {}
}
