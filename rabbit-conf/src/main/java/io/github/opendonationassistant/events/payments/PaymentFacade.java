package io.github.opendonationassistant.events.payments;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class PaymentFacade {

  private final PaymentNotificationSender sender;
  private final ODALogger log = new ODALogger(this);
  private final ObjectMapper mapper;

  @Inject
  public PaymentFacade(PaymentNotificationSender sender, ObjectMapper mapper) {
    this.sender = sender;
    this.mapper = mapper;
  }

  public CompletableFuture<Void> sendEvent(Object event) {
    log.debug("Send PaymentEvent", Map.of("message", event));
    var type = event.getClass().getSimpleName();
    try {
      return sender.sendEvent(type, mapper.writeValueAsBytes(event));
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
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
      byte[] event
    ) {
      return send("event.%s".formatted(type), type, event);
    }
  }
}
