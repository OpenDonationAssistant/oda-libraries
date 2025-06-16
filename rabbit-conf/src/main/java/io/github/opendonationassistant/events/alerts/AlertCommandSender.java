package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.COMMANDS)
public interface AlertCommandSender {
  static ODALogger log = new ODALogger(AlertCommandSender.class);

  void send(@Binding String binding, AlertCommand command);

  default void sendCreateAlertImageCommand(AlertCommand command) {
    log.info("Sending AlertCommand", Map.of("command", command));
    send(Key.ALERTS, command);
  }
}
