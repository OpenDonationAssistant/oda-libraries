package io.github.opendonationassistant.events;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.PAYMENTS)
public interface PaymentNotificationSender {
  void internalSend(@Binding String binding, CompletedPaymentNotification notification);

  default void send(CompletedPaymentNotification payment){
    this.internalSend(Key.PAYMENTS, payment);
  }

  default void sendToActions(CompletedPaymentNotification payment) {
    this.internalSend(Key.ACTIONS, payment);
  }

  default void sendToGoals(CompletedPaymentNotification payment) {
    this.internalSend(Key.DONATIONGOAL, payment);
  }

  default void sendToContributions(CompletedPaymentNotification payment) {
    this.internalSend(Key.CONTRIBUTIONS, payment);
  }

  default void sendToReel(CompletedPaymentNotification payment) {
    this.internalSend(Key.REEL, payment);
  }

  default void sendToDonaton(CompletedPaymentNotification payment) {
    this.internalSend(Key.DONATON, payment);
  }
}
