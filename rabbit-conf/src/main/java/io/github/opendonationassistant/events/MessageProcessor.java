package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.bind.RabbitAcknowledgement;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Singleton
public class MessageProcessor {

  private final ODALogger log = new ODALogger(this);
  private List<MessageHandler> handlers;

  @Inject
  public MessageProcessor(List<MessageHandler> handlers) {
    this.handlers = handlers;
  }

  public void process(String type, byte[] message, RabbitAcknowledgement ack) {
    log.debug("Process message", Map.of("type", type));
    handlers
      .stream()
      .filter(handler -> handler.type().equals(type))
      .findFirst()
      .ifPresent(handler -> {
        try {
          log.debug(
            "Found handler for message",
            Map.of("type", type, "handler", handler.getClass().getSimpleName())
          );
          handler.handle(message);
          ack.ack();
        } catch (IOException e) {
          log.error(
            "Error parsing TwitchChannelRaidEvent",
            Map.of("error", e.getLocalizedMessage())
          );
        }
      });
  }
}
