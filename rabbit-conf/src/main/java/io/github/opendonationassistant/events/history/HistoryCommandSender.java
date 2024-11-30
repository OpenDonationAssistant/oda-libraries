package io.github.opendonationassistant.events.history;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.COMMANDS)
public interface HistoryCommandSender {
  void send(@Binding String binding, HistoryCommand command);

  default void send(HistoryCommand command) {
    send(Key.HISTORY, command);
  }
}
