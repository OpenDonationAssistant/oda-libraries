package io.github.opendonationassistant.events.twitch.events;

public record ChannelRaidEvent(
  String recipientId,
  String fromChannelName,
  Integer viewerCount
) {}
