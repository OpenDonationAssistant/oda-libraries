package io.github.opendonationassistant.events.payments;

import io.micronaut.serde.annotation.Serdeable;

public class PaymentFacade {

  @Serdeable
  public static record ActionRequest(
    String id,
    String actionId,
    String provider,
    Object payload
  ) {}
}
