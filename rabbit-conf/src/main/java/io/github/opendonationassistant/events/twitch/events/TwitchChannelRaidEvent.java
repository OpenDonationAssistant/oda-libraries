package io.github.opendonationassistant.events.twitch.events;

import org.jspecify.annotations.NonNull;

public record TwitchChannelRaidEvent(
  @NonNull String id,
  @NonNull String recipientId,
  @NonNull String fromChannelName,
  @NonNull Integer viewerCount
) {}
