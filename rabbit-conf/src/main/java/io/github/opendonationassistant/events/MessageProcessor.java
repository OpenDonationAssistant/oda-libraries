package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.bind.RabbitAcknowledgement;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Singleton
public class MessageProcessor {

  private final ODALogger log = new ODALogger(this);
  private List<MessageHandler<?>> handlers;

  @Inject
  public MessageProcessor(List<MessageHandler<?>> handlers) {
    this.handlers = handlers;
  }

  @Transactional
  public void process(String type, byte[] message, RabbitAcknowledgement ack) {
    log.debug("Process message", Map.of("type", type));
    var handler = handlers
      .stream()
      .filter(it -> it.type().equals(type))
      .findFirst();
    if (handler.isEmpty()) {
      log.debug("No handler found for message", Map.of("type", type));
      ack.ack();
      return;
    }
    var handlerClass = handler.get().getClass().getCanonicalName();
    log.debug(
      "Found handler for message",
      Map.of("type", type, "handler", handlerClass)
    );
    try {
      handler.get().handle(message);
      log.debug(
        "Message processed",
        Map.of("type", type, "handler", handlerClass)
      );
      ack.ack();
    } catch (Exception e) {
      log.error("Error processing message", e);
    }
  }
}
