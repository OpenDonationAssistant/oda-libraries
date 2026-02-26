package io.github.opendonationassistant.events.history.event;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record GoalHistoryEvent(
  String historyItemId,
  String widgetId,
  String goalId,
  String title
) {}
