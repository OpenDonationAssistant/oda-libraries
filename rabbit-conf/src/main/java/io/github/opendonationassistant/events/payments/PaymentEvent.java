package io.github.opendonationassistant.events.payments;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

@Serdeable
public record PaymentEvent(
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
  public static record ActionRequest(
    String id,
    String actionId,
    Integer amount,
    Map<String, Object> payload
  ) {}

  @Serdeable
  public static record Vote(
    @Nullable String id,
    @Nullable String name,
    Boolean isNew
  ) {}
}
