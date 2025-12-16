package io.github.opendonationassistant.events;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.PAYMENTS)
public interface PaymentNotificationSender {
  void internalSend(
    @Binding String binding,
    @MessageHeader String type,
    CompletedPaymentNotification notification
  );

  default void send(CompletedPaymentNotification payment) {
    this.internalSend(Key.PAYMENTS, "PaymentEvent", payment);
  }

  default void sendToActions(CompletedPaymentNotification payment) {
    this.internalSend(Key.ACTIONS, "PaymentEvent", payment);
  }

  default void sendToGoals(CompletedPaymentNotification payment) {
    this.internalSend(Key.DONATIONGOAL, "PaymentEvent", payment);
  }

  default void sendToContributions(CompletedPaymentNotification payment) {
    this.internalSend(Key.CONTRIBUTIONS, "PaymentEvent", payment);
  }

  default void sendToReel(CompletedPaymentNotification payment) {
    this.internalSend(Key.REEL, "PaymentEvent", payment);
  }

  default void sendToDonaton(CompletedPaymentNotification payment) {
    this.internalSend(Key.DONATON, "PaymentEvent", payment);
  }
}
