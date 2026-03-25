package io.github.opendonationassistant.events.history.event;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record GoalHistoryEvent(
  @Nullable String source,
  @Nullable String originId,
  String recipientId,
  String widgetId,
  String goalId,
  String title,
  Amount amount
)
  implements HasRecipientId {}
