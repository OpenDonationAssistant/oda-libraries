package io.github.opendonationassistant.events.twitch.events;

import java.time.Instant;

import org.jspecify.annotations.NonNull;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelFollowEvent(
  @NonNull String id,
  @NonNull String recipientId,
  @NonNull String username,
  @NonNull Instant timestamp
) {}
