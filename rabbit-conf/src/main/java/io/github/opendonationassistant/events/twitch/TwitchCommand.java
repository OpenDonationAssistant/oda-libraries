package io.github.opendonationassistant.events.twitch;

import io.micronaut.serde.annotation.Serdeable;

public interface TwitchCommand {
  @Serdeable
  public static record SubscribeEvent(String twitchId, String event) {}

  @Serdeable
  public static record UnsubscribeEvent(String twitchId, String event) {}

  @Serdeable
  public static record UnsubscribeAllEvent(String twitchId) {}

  @Serdeable
  public static record LinkAccount(
    String recipientId,
    String twitchId,
    String twitchLogin
  ) {}
}
