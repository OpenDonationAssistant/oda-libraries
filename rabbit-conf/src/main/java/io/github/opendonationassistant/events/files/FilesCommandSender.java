package io.github.opendonationassistant.events.files;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.COMMANDS)
public interface FilesCommandSender {
  void send(@Binding String binding, FilesCommand command);

  default void sendCreateBucketCommand(CreateBucketCommand command) {
    send(Key.FILES, command);
  }

}
