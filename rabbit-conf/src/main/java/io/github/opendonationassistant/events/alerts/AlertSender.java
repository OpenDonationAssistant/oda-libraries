package io.github.opendonationassistant.events.alerts;

import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.AMQ_TOPIC)
public interface AlertSender {
  void internalSend(@Binding String binding, AlertNotification notification);

  default void send(String recipientId, AlertNotification notification) {
    internalSend("%salerts".formatted(notification.recipientId()), notification);
  }
}
