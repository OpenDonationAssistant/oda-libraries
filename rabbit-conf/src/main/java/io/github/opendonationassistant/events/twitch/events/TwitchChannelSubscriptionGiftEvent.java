package io.github.opendonationassistant.events.twitch.events;

import org.jspecify.annotations.NonNull;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelSubscriptionGiftEvent(
  @NonNull String id,
  @NonNull String recipientId,
  @NonNull String username,
  @NonNull String tier,
  @NonNull Number amount,
  @NonNull Number total
) {}
