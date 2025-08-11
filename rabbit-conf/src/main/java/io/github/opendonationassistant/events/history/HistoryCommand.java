package io.github.opendonationassistant.events.history;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record HistoryCommand(
  String type,
  HistoryItemData partial,
  boolean triggerAlert,
  boolean triggerReel,
  boolean triggerDonaton,
  boolean addToTop,
  boolean addToGoal
) {}
