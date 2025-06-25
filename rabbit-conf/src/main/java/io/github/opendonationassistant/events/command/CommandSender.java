package io.github.opendonationassistant.events.command;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient("amq.topic")
public interface CommandSender {
  final ODALogger log = new ODALogger(CommandSender.class);

  void send(@Binding String binding, Command command);

  default void send(Command command) {
    log.info("Send Command", Map.of("command", command));
    send("commands", command);
  }
}
