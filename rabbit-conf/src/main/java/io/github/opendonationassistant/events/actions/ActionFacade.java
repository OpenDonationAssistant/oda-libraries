package io.github.opendonationassistant.events.actions;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class ActionFacade {

  private final ODALogger log = new ODALogger(this);
  private final ActionMessagingClient client;
  private final ObjectMapper mapper;

  @Inject
  public ActionFacade(ActionMessagingClient client, ObjectMapper mapper) {
    this.client = client;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(Object payload) {
    var type = payload.getClass().getSimpleName();
    log.info("Send ActionEvent", Map.of("payload", payload, "type", type));
    try {
      return client.sendMessage(
        "event.%s".formatted(type),
        type,
        mapper.writeValueAsBytes(payload)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> enableAction(
    ActionCommand.EnableActionCommand command
  ) {
    log.info("Send EnableActionCommand", Map.of("command", command));
    try {
      return client.sendCommand(
        "EnableActionCommand",
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public CompletableFuture<Void> disableAction(
    ActionCommand.DisableActionCommand command
  ) {
    log.info("Send DisableActionCommand", Map.of("command", command));
    try {
      return client.sendCommand(
        "DisableActionCommand",
        mapper.writeValueAsBytes(command)
      );
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  @RabbitClient(Exchange.ACTIONS)
  public static interface ActionMessagingClient {
    CompletableFuture<Void> sendMessage(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> sendCommand(
      @MessageHeader String type,
      byte[] payload
    ) {
      return sendMessage(Key.COMMAND, type, payload);
    }
  }
}
