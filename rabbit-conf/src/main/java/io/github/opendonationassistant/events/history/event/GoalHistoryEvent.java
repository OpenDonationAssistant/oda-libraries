package io.github.opendonationassistant.events.history.event;

import org.jspecify.annotations.Nullable;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record GoalHistoryEvent(
  @Nullable String source,
  @Nullable String originId,
  String widgetId,
  String goalId,
  String title
) {}
