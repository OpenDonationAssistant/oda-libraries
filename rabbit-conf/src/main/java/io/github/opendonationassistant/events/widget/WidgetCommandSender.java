package io.github.opendonationassistant.events.widget;

import java.util.Map;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.COMMANDS)
public interface WidgetCommandSender {
  final ODALogger log = new ODALogger(WidgetCommandSender.class);

  void send(@Binding String binding, WidgetUpdateCommand command);

  default void send(WidgetUpdateCommand command) {
    log.info("Send WidgetUpdateCommand", Map.of("command", command));
    send(Key.WIDGETS, command);
  }
}
