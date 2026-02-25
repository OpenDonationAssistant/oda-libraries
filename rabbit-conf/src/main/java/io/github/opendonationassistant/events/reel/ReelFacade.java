package io.github.opendonationassistant.events.reel;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
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

  public CompletableFuture<Void> sendRunResult(ReelRunResult result) {
    log.debug("Send Reel Run Result", Map.of("result", result));
    try {
      return client.sendMessage(
        "event.ReelRunResult",
        "ReelRunResult",
        mapper.writeValueAsBytes(result)
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
