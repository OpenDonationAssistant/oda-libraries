package io.github.opendonationassistant.events.files;

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
public class FilesFacade {

  private final MessagingClient client;
  private final ODALogger log = new ODALogger(this);
  private final ObjectMapper mapper;

  @Inject
  public FilesFacade(MessagingClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(Object payload) {
    var type = payload.getClass().getSimpleName();
    log.info("Send HistoryEvent", Map.of("type", type, "payload", payload));
    try {
      return client.sendEvent(type, mapper.writeValueAsBytes(payload));
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> sendCommand(Object payload) {
    var type = payload.getClass().getSimpleName();
    log.info("Send HistoryCommand", Map.of("type", type, "payload", payload));
    try {
      return client.sendCommand(type, mapper.writeValueAsBytes(payload));
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  @RabbitClient(Exchange.FILES)
  public static interface MessagingClient {
    CompletableFuture<Void> send(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> sendCommand(
      @MessageHeader String type,
      byte[] payload
    ) {
      return send(Key.COMMAND, type, payload);
    }

    default CompletableFuture<Void> sendEvent(
      @MessageHeader String type,
      byte[] payload
    ) {
      return send("event.%s".formatted(type), type, payload);
    }
  }
}
