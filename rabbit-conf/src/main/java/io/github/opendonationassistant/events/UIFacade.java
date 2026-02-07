package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class UIFacade {

  private final ODALogger log = new ODALogger(this);
  private final UIEventSender sender;

  @Inject
  public UIFacade(UIEventSender sender) {
    this.sender = sender;
  }

  public CompletableFuture<Void> sendEvent(String recipientId, Event event) {
    log.debug("Send message to UI", Map.of("recipientId", recipientId,"message", event));
    return CompletableFuture.supplyAsync(() -> {
      try {
        return ObjectMapper.getDefault().writeValueAsBytes(event);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).thenCompose(payload -> {
      return sender.sendEvent(
        "%s.events".formatted(recipientId),
        payload
      );
    });
  }

  @RabbitClient(Exchange.AMQ_TOPIC)
  public static interface UIEventSender {
    CompletableFuture<Void> sendEvent(@Binding String binding, byte[] message);
  }

  @Serdeable
  public static record Event(
    String id,
    String type,
    List<Variable> variables
  ) {}

  @Serdeable
  public static record Variable(
    String id,
    String name,
    Object value,
    String type
  ) {}
}
