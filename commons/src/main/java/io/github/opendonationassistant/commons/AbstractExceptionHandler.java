package io.github.opendonationassistant.commons;

import io.github.opendonationassistant.commons.logging.ODALogger;

public class AbstractExceptionHandler {

  private final ODALogger log = new ODALogger(AbstractExceptionHandler.class);

  protected void log(Exception exception, String id) {
    log.error("Server Error " + id, exception);
  }
}
