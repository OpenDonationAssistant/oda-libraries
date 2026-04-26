package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelRaidEvent(
  String id,
  String recipientId,
  String fromChannelName,
  Integer viewerCount
) {}
