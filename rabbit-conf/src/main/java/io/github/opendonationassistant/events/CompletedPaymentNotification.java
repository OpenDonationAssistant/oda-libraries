package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
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
  Instant authorizationTimestamp
) {}
