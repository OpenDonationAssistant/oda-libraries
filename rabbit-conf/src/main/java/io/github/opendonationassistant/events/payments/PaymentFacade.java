package io.github.opendonationassistant.events.payments;

import static io.github.opendonationassistant.commons.ToString.asBytes;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class PaymentFacade {

  private final PaymentNotificationSender sender;
  private final ODALogger log = new ODALogger(this);

  @Inject
  public PaymentFacade(PaymentNotificationSender sender) {
    this.sender = sender;
  }

  public CompletableFuture<Void> sendEvent(Object event) {
    log.debug("Send PaymentEvent", Map.of("message", event));
    var type = event.getClass().getSimpleName();
    return sender.sendEvent(type, event);
  }

  @RabbitClient(Exchange.PAYMENTS)
  public static interface PaymentNotificationSender {
    CompletableFuture<Void> send(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> sendEvent(
      @MessageHeader String type,
      Object payload
    ) {
      return send("event.%s".formatted(type), type, asBytes(payload));
    }
  }
}
