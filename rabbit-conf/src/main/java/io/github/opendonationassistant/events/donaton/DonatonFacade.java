package io.github.opendonationassistant.events.donaton;

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
public class DonatonFacade {

  private final ODALogger log = new ODALogger(this);
  private final DonatonMessagingClient client;
  private final ObjectMapper mapper;

  @Inject
  public DonatonFacade(DonatonMessagingClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(Object payload) {
    log.info("Send DonatonEvent", Map.of("payload", payload));
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

  @RabbitClient(Exchange.DONATON)
  public static interface DonatonMessagingClient {
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
