package io.github.opendonationassistant.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AbstractExceptionHandler {

  private Logger log = LoggerFactory.getLogger(AbstractExceptionHandler.class);

  protected void log(Exception exception){
    Arrays.asList(exception.getStackTrace())
      .stream()
      .filter(element ->
        element.getClassName().startsWith("io.github.opendonationassistant")
      )
      .findFirst()
      .ifPresent(element ->
        MDC.put(
          "error",
          ToString.asJson(
            Map.of(
              "message",
              collectErrorMessages(exception),
              "location",
              "%s-%s".formatted(
                  Optional.ofNullable(element.getClassName()).orElse(
                    "Unknown class"
                  ),
                  Optional.ofNullable(element.getLineNumber()).orElse(-1)
                )
            )
          )
        )
      );
    log.error("Server Error");
    MDC.clear();
  }

  protected List<String> collectErrorMessages(Throwable exception) {
    var messages = new ArrayList<String>();
    var cause = exception;
    while (cause != null) {
      Optional.ofNullable(cause.getMessage()).ifPresent(messages::add);
      cause = cause.getCause();
    }
    return messages;
  }
}
