package io.github.opendonationassistant.events.config;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RabbitClient("commands")
public interface ConfigCommandSender {
  ODALogger log = new ODALogger(ConfigCommandSender.class);
  ObjectMapper mapper = ObjectMapper.getDefault();

  public default CompletableFuture<Void> send(
    ConfigCommand.PutKeyValue command
  ) throws IOException {
    return send("PutKeyValue", command);
  }

  public default CompletableFuture<Void> send(
    ConfigCommand.UpsertAction command
  ) throws IOException {
    return send("UpsertAction", command);
  }

  public default CompletableFuture<Void> send(
    ConfigCommand.DeleteAction command
  ) throws IOException {
    return send("DeleteAction", command);
  }

  default CompletableFuture<Void> send(String type, Object command)
    throws IOException {
    log.info("Send ConfigCommand", Map.of("type", type, "command", command));
    return send("config", type, mapper.writeValueAsString(command));
  }

  CompletableFuture<Void> send(
    @Binding String binding,
    @MessageHeader String type,
    String command
  );
}
