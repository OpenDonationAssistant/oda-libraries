package io.github.opendonationassistant.events.history.event;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record ReelResultHistoryEvent(
    // TODO rename event and send to Exchange.REEL
  @Nullable String source,
  @Nullable String originId,
  String recipientId,
  String widgetId,
  String optionId,
  String title
) implements HasRecipientId {}
