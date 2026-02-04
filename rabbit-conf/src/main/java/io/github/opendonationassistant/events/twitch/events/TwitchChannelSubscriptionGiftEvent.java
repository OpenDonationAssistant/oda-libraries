package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelSubscriptionGiftEvent(
  String id,
  String recipientId,
  String username,
  String tier,
  Number amount,
  Number total
) {}
