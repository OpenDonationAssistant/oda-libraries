package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;

@Serdeable
public record ChannelCheerEvent(
  String recipientId,
  @Nullable String username,
  String message,
  String bits
) {}
