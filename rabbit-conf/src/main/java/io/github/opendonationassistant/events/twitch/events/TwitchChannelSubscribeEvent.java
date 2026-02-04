package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelSubscribeEvent(
  String id,
  String recipientId,
  String username,
  String tier,
  Boolean isGift
) {}
