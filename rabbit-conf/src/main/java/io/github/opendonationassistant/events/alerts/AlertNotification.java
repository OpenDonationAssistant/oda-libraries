package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;

@Serdeable
public record AlertNotification(
  String id,
  String nickname,
  String message,
  String recipientId,
  Amount amount,
  List<String> attachments,
  String goal,
  Instant authorizationTimestamp,
  AlertMedia media
) {
  @Serdeable
  public static record AlertMedia(String url) {}
}
