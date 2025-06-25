package io.github.opendonationassistant.events.history;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Attachment(
  String id,
  String url,
  String title,
  String thumbnail
) {}
