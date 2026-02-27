package io.github.opendonationassistant.events.history.event;

import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record ReelResultHistoryEvent(
  @Nullable String source,
  @Nullable String originId,
  String widgetId,
  String optionId,
  String title
) {}
