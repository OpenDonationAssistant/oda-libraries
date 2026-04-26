package io.github.opendonationassistant.events.twitch.events;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;

@Serdeable
public record TwitchChannelCheerEvent(
  String id,
  String recipientId,
  @Nullable String username,
  String message,
  String bits
) implements HasRecipientId {}
