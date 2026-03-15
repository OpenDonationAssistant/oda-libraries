package io.github.opendonationassistant.events.actions;

import io.micronaut.serde.annotation.Serdeable;

public interface ActionCommand {
  @Serdeable
  public static record EnableActionCommand(
    String recipientId,
    String actionId
  ) {}

  @Serdeable
  public static record DisableActionCommand(
    String recipientId,
    String actionId
  ) {}
}
