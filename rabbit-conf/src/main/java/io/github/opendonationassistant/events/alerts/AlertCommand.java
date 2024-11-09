package io.github.opendonationassistant.events.alerts;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class AlertCommand {
  private final String paymentId;
  private final String message;
  public AlertCommand(String paymentId, String message) {
    this.paymentId = paymentId;
    this.message = message;
  }
  public String getPaymentId() {
    return paymentId;
  }
  public String getMessage() {
    return message;
  }
}
