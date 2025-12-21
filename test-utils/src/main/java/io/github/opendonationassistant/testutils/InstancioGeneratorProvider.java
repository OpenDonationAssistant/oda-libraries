package io.github.opendonationassistant.testutils;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.events.CompletedPaymentNotification;
import io.micronaut.security.authentication.Authentication;
import org.instancio.Node;
import org.instancio.generator.GeneratorSpec;
import org.instancio.generators.Generators;
import org.instancio.spi.InstancioServiceProvider.GeneratorProvider;
import org.jspecify.annotations.Nullable;

public class InstancioGeneratorProvider implements GeneratorProvider {

  @Override
  public @Nullable GeneratorSpec<?> getGenerator(
    final Node node,
    final Generators gen
  ) {
    final Class<?> targetClass = node.getTargetClass();
    if (targetClass.equals(CompletedPaymentNotification.class)) {
      return new CompletedPaymentNotificationGenerator();
    }
    if (targetClass.equals(Authentication.class)) {
      return new AuthenticationGenerator();
    }
    if (targetClass.equals(Amount.class)) {
      return new AmountGenerator();
    }
    return null;
  }
}
