package io.github.opendonationassistant.events.files;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import io.micronaut.serde.annotation.Serdeable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes(
  {
    @Type(value = CreateBucketCommand.class, name = "createBucket"),
    @Type(value = CopyFileCommand.class, name = "copyFile"),
  }
)
@Serdeable
public interface FilesCommand {}
