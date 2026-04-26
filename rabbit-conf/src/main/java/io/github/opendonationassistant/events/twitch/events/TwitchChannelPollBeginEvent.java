package io.github.opendonationassistant.events.twitch.events;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TwitchChannelPollBeginEvent(String id, String recipientId) implements HasRecipientId {}
