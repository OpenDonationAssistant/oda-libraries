package io.github.opendonationassistant.events.config;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import java.util.Map;

@RabbitClient("commands")
public interface ConfigCommandSender {
  ODALogger log = new ODALogger(ConfigCommandSender.class);

  void send(@Binding String binding, ConfigPutCommand command);

  void sendSerialized(@Binding String binding, String command);

  default void send(ConfigPutCommand command) {
    log.context(Map.of("command", command));
    var mapper = ObjectMapper.getDefault();
    try {
      sendSerialized("config", mapper.writeValueAsString(command));
    } catch (IOException e) {
      log.info("Can't serialize command", Map.of("command", command));
      e.printStackTrace();
    }
    log.clear();
  }
}
