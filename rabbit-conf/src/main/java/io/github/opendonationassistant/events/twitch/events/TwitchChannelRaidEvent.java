package io.github.opendonationassistant.events.twitch.events;

public record TwitchChannelRaidEvent(
  String id,
  String recipientId,
  String fromChannelName,
  Integer viewerCount
) {}
