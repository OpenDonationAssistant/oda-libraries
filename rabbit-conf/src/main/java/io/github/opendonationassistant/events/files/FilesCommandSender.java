package io.github.opendonationassistant.events.files;

import static io.github.opendonationassistant.commons.ToString.asBytes;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.events.files.FilesCommand.CopyFileCommand;
import io.github.opendonationassistant.events.files.FilesCommand.CreateBucketCommand;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.FILES)
public interface FilesCommandSender {
  final ODALogger log = new ODALogger(FilesCommandSender.class);

  void send(
    @Binding String binding,
    @MessageHeader String type,
    byte[] command
  );

  default void sendCreateBucketCommand(CreateBucketCommand command) {
    log.info("Send CreateBucketCommand", Map.of("command", command));
    send(Key.COMMAND, "create-bucket", asBytes(command));
  }

  default void sendCopyFileCommand(CopyFileCommand command) {
    log.info("Send CreateBucketCommand", Map.of("command", command));
    send(Key.COMMAND, "copy-file", asBytes(command));
  }
}
