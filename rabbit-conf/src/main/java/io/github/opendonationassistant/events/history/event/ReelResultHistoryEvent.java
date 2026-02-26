package io.github.opendonationassistant.events.history.event;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ReelResultHistoryEvent (
  String historyItemId,
  String widgetId,
  String optionId,
  String title
) {}
