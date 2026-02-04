package io.github.opendonationassistant.events.twitch.events;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import org.jspecify.annotations.NonNull;

@Serdeable
public record TwitchChannelSubscriptionMessageEvent(
  @NonNull String id,
  @NonNull String recipientId,
  @NonNull String username,
  @NonNull String tier,
  @NonNull Message message,
  @NonNull Integer cumulativeMonths,
  @NonNull Integer totalMonths,
  @NonNull Integer streakMonths
) {
  @Serdeable
  public static record Message(@NonNull String text, List<Emote> emotes) {}

  @Serdeable
  public static record Emote(Integer begin, Integer end, String id) {}
}
