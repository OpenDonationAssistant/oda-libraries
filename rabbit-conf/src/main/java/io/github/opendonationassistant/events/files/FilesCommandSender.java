package io.github.opendonationassistant.events.files;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.COMMANDS)
public interface FilesCommandSender {
  final ODALogger log = new ODALogger(FilesCommandSender.class);

  void send(@Binding String binding, FilesCommand command);

  default void sendCreateBucketCommand(CreateBucketCommand command) {
    log.info("Send CreateBucketCommand", Map.of("command", command));
    send(Key.FILES, command);
  }
}
