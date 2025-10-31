package io.github.opendonationassistant.events.actions;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Map;

@RabbitClient(Exchange.AMQ_TOPIC)
public interface ActionSender {
  static ODALogger log = new ODALogger(ActionSender.class);

  void internalSend(@Binding String binding, List<ActionRequest> request);

  default void send(String recipientId, List<ActionRequest> request) {
    log.info("Send ActionRequest", Map.of("request", request));
    internalSend("%s.actions".formatted(recipientId), request);
  }

  @Serdeable
  public static record ActionRequest(
    String id,
    String actionId,
    String provider,
    String nickname,
    Map<String, Object> payload
  ) {}
}
