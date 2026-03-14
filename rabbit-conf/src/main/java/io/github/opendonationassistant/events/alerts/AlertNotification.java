package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record AlertNotification(
  String id,
  @Nullable String nickname,
  @Nullable String message,
  String recipientId,
  @Nullable Amount amount,
  @Nullable AlertMedia media,
  String system
) {
  @Serdeable
  public static record AlertMedia(String url) {}
}
