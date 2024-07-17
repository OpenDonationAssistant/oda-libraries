package io.github.opendonationassistant.events;

import io.github.opendonationassistant.RabbitConfiguration;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(RabbitConfiguration.PAYMENTS_EXCHANGE_NAME)
public interface PaymentNotificationSender {
  void send(@Binding String binding, CompletedPaymentNotification notification);

  default void sendToGoals(CompletedPaymentNotification payment) {
    this.send(RabbitConfiguration.PAYMENT_GOAL_ROUTING_KEY, payment);
  }

  default void sendToContributions(CompletedPaymentNotification payment) {
    this.send(RabbitConfiguration.PAYMENT_CONTRIBUTIONS_ROUTING_KEY, payment);
  }

  default void sendToReel(CompletedPaymentNotification payment) {
    this.send(RabbitConfiguration.PAYMENT_REEL_ROUTING_KEY, payment);
  }
}
