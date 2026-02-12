package io.github.opendonationassistant.events.history;

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
public class HistoryFacade {

  private final HistoryMessagingClient client;
  private final ODALogger log = new ODALogger(this);

  @Inject
  public HistoryFacade(HistoryMessagingClient client) {
    this.client = client;
  }

  public CompletableFuture<Void> sendEvent(Object payload) {
    log.info("Send HistoryEvent", Map.of("payload", payload));
    var type = payload.getClass().getSimpleName();
    return client.sendEvent(type, payload);
  }

  @RabbitClient(Exchange.TWITCH)
  public static interface HistoryMessagingClient {
    CompletableFuture<Void> send(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> sendCommand(
      @MessageHeader String type,
      Object payload
    ) {
      return send(Key.COMMAND, type, asBytes(payload));
    }

    default CompletableFuture<Void> sendEvent(
      @MessageHeader String type,
      Object payload
    ) {
      return send("event.%s".formatted(type), type, asBytes(payload));
    }
  }
}
