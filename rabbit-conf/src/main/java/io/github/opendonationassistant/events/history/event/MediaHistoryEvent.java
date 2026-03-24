package io.github.opendonationassistant.events.history.event;

import org.jspecify.annotations.Nullable;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record MediaHistoryEvent (
  @Nullable String source,
  @Nullable String originId,
  String recipientId,
  String mediaId,
  String url,
  String title,
  String thumbnail
) implements HasRecipientId {}
