package io.github.opendonationassistant.testutils;

import org.instancio.Random;
import org.instancio.generator.Generator;

import io.github.opendonationassistant.commons.Amount;

public class AmountGenerator implements Generator<Amount>{

  @Override
  public Amount generate(Random random) {
    return new Amount(random.intRange(0, 1000), 0, "RUB");
  }
}
