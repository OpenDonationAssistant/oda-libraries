package io.github.opendonationassistant.testutils;

import org.instancio.Random;
import org.instancio.junit.GivenProvider;

public class RecipientIdProvider implements GivenProvider {

  @Override
  public Object provide(ElementContext context) {
    Random random = context.random();
    return random.alphanumeric(10);
  }
}
