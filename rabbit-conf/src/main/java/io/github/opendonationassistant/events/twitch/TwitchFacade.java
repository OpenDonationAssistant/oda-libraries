package io.github.opendonationassistant.events.twitch;

import static io.github.opendonationassistant.commons.ToString.asBytes;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class TwitchFacade {

  private final ODALogger log = new ODALogger(this);
  private final TwitchMessagingClient client;

  @Inject
  public TwitchFacade(TwitchMessagingClient client) {
    this.client = client;
  }

  public CompletableFuture<Void> subscribe(
    TwitchCommand.SubscribeEvent command
  ) {
    log.info("Send SubscribeEvent", Map.of("command", command));
    return client.sendCommand("SubscribeEvent", command);
  }

  public CompletableFuture<Void> unsubscribe(
    TwitchCommand.UnsubscribeAllEvent command
  ) {
    log.info("Send UnsubscribeAllEvent", Map.of("command", command));
    return client.sendCommand("UnsubscribeAllEvent", command);
  }

  public CompletableFuture<Void> unsubscribe(
    TwitchCommand.UnsubscribeEvent command
  ) {
    log.info("Send UnsubscribeEvent", Map.of("command", command));
    return client.sendCommand("UnsubscribeEvent", command);
  }

  @RabbitClient(Exchange.TWITCH)
  public static interface TwitchMessagingClient {
    CompletableFuture<Void> sendMessage(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> send(
      @Binding String binding,
      @MessageHeader String type,
      Object payload
    ) {
      return sendMessage(binding, type, asBytes(payload));
    }

    default CompletableFuture<Void> sendCommand(
      @MessageHeader String type,
      Object payload
    ) {
      return sendMessage(Key.COMMAND, type, asBytes(payload));
    }
  }
}
