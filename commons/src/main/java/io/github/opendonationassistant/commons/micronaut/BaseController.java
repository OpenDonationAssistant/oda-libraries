package io.github.opendonationassistant.commons.micronaut;

import io.micronaut.security.authentication.Authentication;

public abstract class BaseController {

  protected String getOwnerId(Authentication auth) {
    return String.valueOf(
      auth.getAttributes().getOrDefault("preferred_username", "")
    );
  }

}
