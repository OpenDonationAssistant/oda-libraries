package io.github.opendonationassistant.events.ui;

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
  private final ObjectMapper mapper;

  @Inject
  public UIFacade(UIEventSender sender, ObjectMapper mapper) {
    this.sender = sender;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(String recipientId, Event event) {
    log.debug(
      "Send message to UI",
      Map.of("recipientId", recipientId, "message", event)
    );
    try {
      return sender.sendEvent(
        "%s.events".formatted(recipientId),
        mapper.writeValueAsBytes(event)
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
