package io.github.opendonationassistant.events;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("amq.topic")
@Deprecated
public interface PaymentSender {
  void send(@Binding String binding, CompletedPaymentNotification notification);
}

