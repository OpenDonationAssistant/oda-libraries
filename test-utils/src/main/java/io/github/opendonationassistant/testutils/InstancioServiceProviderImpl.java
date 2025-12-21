package io.github.opendonationassistant.testutils;

import org.instancio.spi.InstancioServiceProvider;

public class InstancioServiceProviderImpl implements InstancioServiceProvider {

  @Override
  public GeneratorProvider getGeneratorProvider() {
    return new InstancioGeneratorProvider();
  }

}
