package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record ChannelSubscriptionMessageEvent(
  String recipientId,
  String username,
  String tier,
  Message message,
  Integer cumulativeMonths,
  Integer totalMonths,
  Integer streakMonths
) {
  @Serdeable
  public static record Message(String text, List<Emote> emotes) {}

  @Serdeable
  public static record Emote(Integer begin, Integer end, String id) {}
}
