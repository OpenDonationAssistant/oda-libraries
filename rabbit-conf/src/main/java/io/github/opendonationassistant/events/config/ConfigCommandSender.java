package io.github.opendonationassistant.events.config;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient("commands")
public interface ConfigCommandSender {
  ODALogger log = new ODALogger(ConfigCommandSender.class);

  void send(@Binding String binding, ConfigPutCommand command);

  default void send(ConfigPutCommand command) {
    log.context(Map.of("command", command));
    send("config", command);
    log.clear();
  }

}
