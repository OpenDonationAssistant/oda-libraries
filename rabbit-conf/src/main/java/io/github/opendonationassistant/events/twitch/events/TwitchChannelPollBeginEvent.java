package io.github.opendonationassistant.events.twitch.events;

import org.jspecify.annotations.NonNull;

public record TwitchChannelPollBeginEvent(
    @NonNull String id,
    @NonNull String recipientId
) {}
