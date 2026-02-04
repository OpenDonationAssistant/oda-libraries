package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NonNull;

@Serdeable
public record TwitchChannelCheerEvent(
  @NonNull String id,
  @NonNull String recipientId,
  @Nullable String username,
  @NonNull String message,
  @NonNull String bits
) {}
