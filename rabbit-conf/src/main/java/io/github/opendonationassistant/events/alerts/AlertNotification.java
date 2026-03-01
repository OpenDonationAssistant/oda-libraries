package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

@Serdeable
public record AlertNotification(
  String id,
  String type,
  @Nullable String nickname,
  @Nullable String message,
  String recipientId,
  @Nullable Amount amount,
  List<String> attachments,
  @Nullable String goal,
  List<ActionRequest> actions,
  Instant authorizationTimestamp,
  @Nullable AlertMedia media,
  String system
) {
  public AlertNotification withMedia(AlertMedia newMedia) {
    return new AlertNotification(
      id,
      type,
      nickname,
      message,
      recipientId,
      amount,
      attachments,
      goal,
      actions,
      authorizationTimestamp,
      newMedia,
      system
    );
  }

  @Serdeable
  public static record AlertMedia(String url) {}

  @Serdeable
  public record ActionRequest(
    String id,
    String actionId,
    String name,
    Integer amount,
    Map<String, Object> payload
  ) {}
}
