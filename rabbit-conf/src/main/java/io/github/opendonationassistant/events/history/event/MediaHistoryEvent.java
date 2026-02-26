package io.github.opendonationassistant.events.history.event;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record MediaHistoryEvent (
  String historyItemId,
  String mediaId,
  String url,
  String title,
  String thumbnail
) {}
