package io.github.opendonationassistant.events.history.event;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record DeletedHistoryItem(
  String historyItemId,
  String recipientId,
  String system,
  @Nullable String originId
)
  implements HasRecipientId {}
