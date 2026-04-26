package io.github.opendonationassistant.events.twitch.events;

import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record TwitchChannelSubscriptionMessageEvent(
  String id,
  String recipientId,
  String username,
  String tier,
  Message message,
  Integer cumulativeMonths,
  Integer totalMonths,
  Integer streakMonths
)
  implements HasRecipientId {
  @Serdeable
  public static record Message(String text, List<Emote> emotes) {}

  @Serdeable
  public static record Emote(Integer begin, Integer end, String id) {}
}
