package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ChannelSubscribeEvent(
  String recipientId,
  String username,
  String tier,
  Boolean isGift
) {}
