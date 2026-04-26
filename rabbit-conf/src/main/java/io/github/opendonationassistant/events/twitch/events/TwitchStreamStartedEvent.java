package io.github.opendonationassistant.events.twitch.events;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchStreamStartedEvent(String id, String recipientId, String thumbnailUrl) implements HasRecipientId {}
