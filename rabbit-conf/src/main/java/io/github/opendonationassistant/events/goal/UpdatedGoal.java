package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record UpdatedGoal(
  String goalId,
  String widgetId,
  String recipientId,
  String fullDescription,
  String briefDescription,
  Amount requiredAmount,
  Amount accumulatedAmount,
  Boolean isDefault
) {}
