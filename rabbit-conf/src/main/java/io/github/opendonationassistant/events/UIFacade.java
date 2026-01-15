package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RabbitClient(Exchange.AMQ_TOPIC)
public class UIFacade {

  private final ODALogger log = new ODALogger(this);
  private final UIEventSender sender;

  @Inject
  public UIFacade(UIEventSender sender) {
    this.sender = sender;
  }

  public CompletableFuture<Void> sendEvent(String recipientId, Object message) {
    log.debug("Send UIEvent", Map.of("message", message));
    return CompletableFuture.supplyAsync(() -> {
      var wrapped = new Wrapper(message.getClass().getSimpleName(), message);
      try {
        return ObjectMapper.getDefault().writeValueAsBytes(wrapped);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }).thenCompose(payload ->
      sender.sendEvent("/topic/%s.events".formatted(recipientId), payload)
    );
  }

  public static interface UIEventSender {
    CompletableFuture<Void> sendEvent(@Binding String binding, byte[] message);
  }

  @Serdeable
  public static record Wrapper(String type, Object data) {}
}
