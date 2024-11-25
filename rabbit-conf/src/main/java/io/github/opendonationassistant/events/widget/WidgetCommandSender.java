package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.COMMANDS)
public interface WidgetCommandSender {
  void send(@Binding String binding, WidgetUpdateCommand command);

  default void send(WidgetUpdateCommand command) {
    send(Key.WIDGETS, command);
  }
}
