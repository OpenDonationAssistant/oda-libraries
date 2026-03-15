package io.github.opendonationassistant.events.actions;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record ActionHistoryEvent(
  String source,
  String originId,
  List<ActionRequest> actions
) {
  @Serdeable
  public static record ActionRequest(
    String actionId,
    String name,
    Integer amount
  ) {}
}
