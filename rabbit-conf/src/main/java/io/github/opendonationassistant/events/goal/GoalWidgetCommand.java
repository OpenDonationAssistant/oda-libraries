package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record GoalWidgetCommand(
  String type,
  String goalId,
  String fullDescription,
  String briefDescription,
  Amount requiredAmount,
  Amount accumulatedAmount
) {}
