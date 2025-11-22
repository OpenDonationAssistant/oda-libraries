package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.NonNull;

@Serdeable
public record AlertNotification(
  String id,
  String nickname,
  String message,
  String recipientId,
  Amount amount,
  @NonNull List<String> attachments,
  String goal,
  @NonNull List<ActionRequest> actions,
  Instant authorizationTimestamp,
  AlertMedia media,
  String system
) {
  public AlertNotification withMedia(AlertMedia newMedia) {
    return new AlertNotification(
      id,
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
