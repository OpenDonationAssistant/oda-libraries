package io.github.opendonationassistant.events.twitch;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class TwitchFacade {

  private final ODALogger log = new ODALogger(this);
  private final TwitchMessagingClient client;
  private final ObjectMapper mapper;

  @Inject
  public TwitchFacade(TwitchMessagingClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(Object payload) {
    log.info("Send TwitchEvent", Map.of("payload", payload));
    var type = payload.getClass().getSimpleName();
    try {
      return client.sendMessage(
        "event.%s".formatted(type),
        type,
        mapper.writeValueAsBytes(payload)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> subscribe(
    TwitchCommand.SubscribeEvent command
  ) {
    log.info("Send SubscribeCommand", Map.of("command", command));
    try {
      return client.sendCommand(
        "SubscribeEvent",
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> unsubscribe(
    TwitchCommand.UnsubscribeAllEvent command
  ) {
    log.info("Send UnsubscribeAllCommand", Map.of("command", command));
    try {
      return client.sendCommand(
        "UnsubscribeAllEvent",
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> unsubscribe(
    TwitchCommand.UnsubscribeEvent command
  ) {
    log.info("Send UnsubscribeCommand", Map.of("command", command));
    try {
      return client.sendCommand(
        "UnsubscribeEvent",
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> link(TwitchCommand.LinkAccount command) {
    log.info("Send LinkCommand", Map.of("command", command));
    try {
      return client.sendCommand(
        "LinkAccount",
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  @RabbitClient(Exchange.TWITCH)
  public static interface TwitchMessagingClient {
    CompletableFuture<Void> sendMessage(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> sendCommand(
      @MessageHeader String type,
      byte[] payload
    ) {
      return sendMessage(Key.COMMAND, type, payload);
    }
  }
}
