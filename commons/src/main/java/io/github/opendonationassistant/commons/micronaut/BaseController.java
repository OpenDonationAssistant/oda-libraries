package io.github.opendonationassistant.commons.micronaut;

import io.micronaut.security.authentication.Authentication;
import java.util.Optional;

import org.jspecify.annotations.Nullable;

public abstract class BaseController {

  protected Optional<String> getOwnerId(@Nullable Authentication auth) {
    return Optional.ofNullable(auth)
      .map(Authentication::getAttributes)
      .map(it -> it.get("preferred_username"))
      .map(String::valueOf);
  }

}
