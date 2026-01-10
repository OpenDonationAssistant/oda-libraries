package io.github.opendonationassistant.events.reel;

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
public class ReelFacade {

  private final ODALogger log = new ODALogger(this);
  private final ReelMessagingClient client;

  @Inject
  public ReelFacade(ReelMessagingClient client) {
    this.client = client;
  }

  public CompletableFuture<Void> sendRunResult(ReelRunResult result) {
    log.debug("Send Reel Run Result", Map.of("result", result));
    return client.send("event.ReelRunResult", "ReelRunResult", result);
  }

  @RabbitClient(Exchange.REEL)
  public static interface ReelMessagingClient {
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
  }
}
