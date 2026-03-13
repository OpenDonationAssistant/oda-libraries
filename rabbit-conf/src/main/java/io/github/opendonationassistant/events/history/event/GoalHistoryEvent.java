package io.github.opendonationassistant.events.history.event;

import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record GoalHistoryEvent(
  @Nullable String source,
  @Nullable String originId,
  String widgetId,
  String goalId,
  String title
) {}
