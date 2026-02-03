package io.github.opendonationassistant.events.twitch.events;

import java.time.Instant;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelFollowEvent(
  String id,
  String recipientId,
  String username,
  Instant timestamp
) {}
