package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.AMQ_TOPIC)
public interface AlertSender {
  static ODALogger log = new ODALogger(AlertSender.class);

  void internalSend(@Binding String binding, AlertNotification notification);

  default void send(String recipientId, AlertNotification notification) {
    log.info("Sending AlertNotification", Map.of("notification", notification));
    internalSend(
      "%salerts".formatted(notification.recipientId()),
      notification
    );
  }
}
