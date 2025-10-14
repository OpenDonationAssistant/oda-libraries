package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.events.alerts.AlertNotification;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nonnull;

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
  String goal,
  Instant authorizationTimestamp,
  @Nonnull String system
) {
  public AlertNotification asAlertNotification() {
    return new AlertNotification(
      id,
      cleanNickname,
      cleanMessage,
      recipientId,
      amount,
      attachments,
      goal,
      authorizationTimestamp,
      null,
      system
    );
  }
}
