package io.github.opendonationassistant.events.history.event;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

@Serdeable
public record HistoryItemEvent(
  String id,
  String type,
  String recipientId,
  String system,
  String originId,
  Instant timestamp,
  @Nullable String nickname,
  @Nullable Amount amount,
  @Nullable String message,
  List<String> goals,
  List<ActionRequest> actions,
  @Nullable Vote vote
) {

  @Serdeable
  public static record ActionRequest(
    String id,
    String actionId,
    String name,
    Integer amount,
    Map<String, Object> payload
  ) {}

  @Serdeable
  public static record Vote(@Nullable String id, String name, Boolean isNew) {}
}
