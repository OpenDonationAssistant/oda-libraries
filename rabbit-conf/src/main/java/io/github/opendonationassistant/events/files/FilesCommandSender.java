package io.github.opendonationassistant.events.files;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.github.opendonationassistant.RabbitConfiguration;

@RabbitClient(RabbitConfiguration.Exchange.COMMANDS)
public interface FilesCommandSender {
  void send(@Binding String binding, FilesCommand command);

  default void sendCreateBucketCommand(CreateBucketCommand command) {
    send(RabbitConfiguration.Key.FILES, command);
  }

}
