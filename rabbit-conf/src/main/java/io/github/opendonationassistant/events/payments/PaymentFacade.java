package io.github.opendonationassistant.events.payments;

import java.util.Map;

import io.micronaut.serde.annotation.Serdeable;

public class PaymentFacade {

  @Serdeable
  public static record ActionRequest(
    String id,
    String actionId,
    Integer amount,
    Map<String, Object> payload
  ) {}
}
