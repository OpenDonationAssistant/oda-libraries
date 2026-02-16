package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import java.time.Instant;
import java.util.List;

@Serdeable
public record CompletedPaymentNotification(
  String id,
  String nickname,
  String cleanNickname,
  String message,
  String cleanMessage,
  String recipientId,
  Amount amount,
  List<String> attachments,
  @Nullable String goal,
  Instant authorizationTimestamp,
  List<ActionRequest> actions,
  @Nullable Vote vote
) {

  @Serdeable
  public static record Vote(@Nullable String id, String name, Boolean isNew) {}
}
