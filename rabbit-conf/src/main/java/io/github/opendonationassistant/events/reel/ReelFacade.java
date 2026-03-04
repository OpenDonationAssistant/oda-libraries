package io.github.opendonationassistant.events.reel;

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
public class ReelFacade {

  private final ODALogger log = new ODALogger(this);
  private final ReelMessagingClient client;
  private final ObjectMapper mapper;

  @Inject
  public ReelFacade(ReelMessagingClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(Object event) {
    var type = event.getClass().getSimpleName();
    log.debug("Send ReelEvent", Map.of("event", event));
    try {
      return client.sendMessage(
        "event.%s".formatted(type),
        type,
        mapper.writeValueAsBytes(event)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> sendCommand(Object command) {
    var type = command.getClass().getSimpleName();
    log.info("Send ReelCommand", Map.of("type", type, "command", command));
    try {
      return client.sendMessage(
        Key.COMMAND,
        type,
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  @RabbitClient(Exchange.REEL)
  public static interface ReelMessagingClient {
    CompletableFuture<Void> sendMessage(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );
  }
}
