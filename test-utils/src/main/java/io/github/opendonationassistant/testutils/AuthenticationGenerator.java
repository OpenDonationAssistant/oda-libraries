package io.github.opendonationassistant.testutils;

import io.micronaut.security.authentication.Authentication;
import java.util.Map;
import org.instancio.Random;
import org.instancio.generator.Generator;

public class AuthenticationGenerator implements Generator<Authentication> {

  @Override
  public Authentication generate(Random random) {
    return Authentication.build(
      random.alphanumeric(10),
      Map.of("preferred_username", "testuser")
    );
  }
}
