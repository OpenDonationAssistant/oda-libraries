package io.github.opendonationassistant.events.command;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("amq.topic")
public interface CommandSender {
  void send(@Binding String binding, Command command);

  default void send(Command command) {
    send("commands", command);
  }
}
