package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.events.alerts.AlertNotification;
import io.github.opendonationassistant.events.payments.PaymentFacade.ActionRequest;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nonnull;
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
  String goal,
  Instant authorizationTimestamp,
  @Nonnull String system,
  @Nonnull List<ActionRequest> actions,
  @Nullable Vote vote
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
      actions
        .stream()
        .map(it ->
          new AlertNotification.ActionRequest(
            it.id(),
            it.actionId(),
            "", // TODO use HistoryItem for alerts
            it.amount(),
            it.payload()
          )
        )
        .toList(),
      authorizationTimestamp,
      null,
      system
    );
  }

  @Serdeable
  public static record Vote(@Nullable String id, String name, Boolean isNew){}
}
