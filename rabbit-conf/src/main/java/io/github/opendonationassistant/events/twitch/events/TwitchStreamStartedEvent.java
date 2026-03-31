package io.github.opendonationassistant.events.twitch.events;

public record TwitchStreamStartedEvent(String id, String recipientId, String thumbnailUrl) {}
