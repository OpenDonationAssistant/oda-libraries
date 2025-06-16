package io.github.opendonationassistant.events.config;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ConfigPutCommand(
  String ownerId,
  String name,
  String key,
  Object value
) {}
