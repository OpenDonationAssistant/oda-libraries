package io.github.opendonationassistant.events.reel;

import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

public class ReelCommand {

  @Serdeable
  public static record TriggerReelCommand(
    String widgetId,
    String recipientId,
    String source,
    @Nullable String originId
  ) {}
}
