package io.github.opendonationassistant.events.history;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.COMMANDS)
public interface HistoryCommandSender {
  final ODALogger log = new ODALogger(HistoryCommandSender.class);

  void send(@Binding String binding, HistoryCommand command);

  default void send(HistoryCommand command) {
    log.info("Send HistoryCommand", Map.of("command", command));
    send(Key.HISTORY, command);
  }
}
