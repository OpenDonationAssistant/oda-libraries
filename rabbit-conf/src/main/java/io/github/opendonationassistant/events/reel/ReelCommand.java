package io.github.opendonationassistant.events.reel;

import io.micronaut.serde.annotation.Serdeable;

public class ReelCommand {

  @Serdeable
  public static record TriggerReelCommand(
    String widgetId,
    String recipientId
  ) {}
}
