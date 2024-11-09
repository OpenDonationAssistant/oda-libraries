package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.COMMANDS)
public interface AlertCommandSender {
  void send(@Binding String binding, AlertCommand command);

  default void sendCreateAlertImageCommand(AlertCommand command) {
    send(Key.ALERTS, command);
  }
}
