package io.github.opendonationassistant.events.twitch.events;

import org.jspecify.annotations.NonNull;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelSubscribeEvent(
  @NonNull String id,
  @NonNull String recipientId,
  @NonNull String username,
  @NonNull String tier,
  @NonNull Boolean isGift
) {}
