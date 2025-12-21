package io.github.opendonationassistant.testutils;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.events.CompletedPaymentNotification;
import java.time.Instant;
import java.util.List;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class CompletedPaymentNotificationGenerator
  implements Generator<CompletedPaymentNotification> {

  @Override
  public CompletedPaymentNotification generate(Random random) {
    return new CompletedPaymentNotification(
      random.alphanumeric(10),
      random.alphanumeric(10),
      random.alphanumeric(10),
      random.alphanumeric(10),
      random.alphanumeric(10),
      random.alphanumeric(10),
      new Amount(100, 0, "RUB"),
      List.of(),
      random.alphanumeric(10),
      Instant.now(),
      random.alphanumeric(10),
      List.of(),
      null
    );
  }
}
