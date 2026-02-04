package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelSubscriptionGift(
  String id,
  String recipientId,
  String username,
  String tier,
  Number amount,
  Number total
) {}
