package io.github.opendonationassistant.events.history;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.events.HasRecipientId;
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
public class HistoryFacade {

  private final HistoryMessagingClient client;
  private final ODALogger log = new ODALogger(this);
  private final ObjectMapper mapper;

  @Inject
  public HistoryFacade(HistoryMessagingClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(HasRecipientId payload) {
    log.info("Send HistoryEvent", Map.of("payload", payload));
    var type = payload.getClass().getSimpleName();
    try {
      return client.sendEvent(
        "event.%s".formatted(type),
        type,
        payload.recipientId(),
        mapper.writeValueAsBytes(payload)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> sendCommand(Object payload) {
    log.info("Send HistoryCommand", Map.of("payload", payload));
    var type = payload.getClass().getSimpleName();
    try {
      return client.sendCommand(type, mapper.writeValueAsBytes(payload));
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  @RabbitClient(Exchange.HISTORY)
  public static interface HistoryMessagingClient {
    @Binding(Key.COMMAND)
    CompletableFuture<Void> sendCommand(
      @MessageHeader String type,
      byte[] command
    );

    CompletableFuture<Void> sendEvent(
      @Binding String binding,
      @MessageHeader String type,
      @MessageHeader String recipientId,
      byte[] command
    );
  }
}
