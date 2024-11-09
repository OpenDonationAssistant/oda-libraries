package io.github.opendonationassistant.events;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.PAYMENTS)
public interface PaymentNotificationSender {
  void send(@Binding String binding, CompletedPaymentNotification notification);

  default void sendToGoals(CompletedPaymentNotification payment) {
    this.send(Key.GOAL, payment);
  }

  default void sendToContributions(CompletedPaymentNotification payment) {
    this.send(Key.CONTRIBUTIONS, payment);
  }

  default void sendToReel(CompletedPaymentNotification payment) {
    this.send(Key.REEL, payment);
  }

  default void sendToDonaton(CompletedPaymentNotification payment) {
    this.send(Key.DONATON, payment);
  }
}
