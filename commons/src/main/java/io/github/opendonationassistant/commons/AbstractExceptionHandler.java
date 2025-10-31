package io.github.opendonationassistant.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class AbstractExceptionHandler {

  private Logger log = LoggerFactory.getLogger(AbstractExceptionHandler.class);

  protected void log(Exception exception) {
    Arrays.asList(exception.getStackTrace())
      .stream()
      .filter(element ->
        element.getClassName().startsWith("io.github.opendonationassistant")
      )
      .findFirst()
      .ifPresentOrElse(
        element -> putMDC(element, exception),
        () -> putMDC(exception.getStackTrace()[0], exception)
      );
    log.error("Server Error");
    if (log.isDebugEnabled()) {
      exception.printStackTrace();
    }
    MDC.clear();
  }

  private void putMDC(StackTraceElement element, Exception exception) {
    MDC.put(
      "error",
      """
        {"message":"%s"},
        {"location":"%s:%s"}
      """.formatted(
          collectErrorMessages(exception),
          Optional.ofNullable(element.getClassName()).orElse("Unknown class"),
          element.getLineNumber()
        )
    );
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
