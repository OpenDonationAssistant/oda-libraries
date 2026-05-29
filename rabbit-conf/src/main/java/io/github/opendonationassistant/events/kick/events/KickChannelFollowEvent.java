package io.github.opendonationassistant.events.kick.events;

import java.time.Instant;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record KickChannelFollowEvent(
  String id,
  String recipientId,
  String username,
  Instant timestamp
) implements HasRecipientId {}
