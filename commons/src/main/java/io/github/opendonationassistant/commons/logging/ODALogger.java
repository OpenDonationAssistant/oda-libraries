package io.github.opendonationassistant.commons.logging;

import io.github.opendonationassistant.commons.ToString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ODALogger {

  final org.slf4j.Logger log;

  public ODALogger(Object context) {
    this.log = LoggerFactory.getLogger(context.getClass());
  }

  public ODALogger(Class<?> className) {
    this.log = LoggerFactory.getLogger(className);
  }

  public void context(Map<String, ?> context) {
    MDC.put("context", ToString.asJson(context));
  }

  public void clear() {
    MDC.remove("context");
  }

  public void info(String message) {
    if (!log.isInfoEnabled()) {
      return;
    }
    this.log.info(message);
  }

  public void info(String message, Map<String, ?> context) {
    if (!log.isInfoEnabled()) {
      return;
    }
    this.context(context);
    this.log.info(message);
    this.clear();
  }

  public void info(String message, Supplier<Map<String, ?>> context) {
    if (!log.isInfoEnabled()) {
      return;
    }
    this.context(context.get());
    this.log.info(message);
    this.clear();
  }

  public void debug(String message) {
    if (!log.isDebugEnabled()) {
      return;
    }
    this.log.debug(message);
  }

  public void debug(String message, Map<String, ?> context) {
    if (!log.isDebugEnabled()) {
      return;
    }
    this.context(context);
    this.log.debug(message);
    this.clear();
  }

  public void debug(String message, Supplier<Map<String, ?>> context) {
    if (!log.isDebugEnabled()) {
      return;
    }
    this.context(context.get());
    this.log.debug(message);
    this.clear();
  }

  public void warn(String message) {
    if (!log.isWarnEnabled()) {
      return;
    }
    this.log.warn(message);
  }

  public void warn(String message, Map<String, ?> context) {
    if (!log.isWarnEnabled()) {
      return;
    }
    this.context(context);
    this.log.warn(message);
    this.clear();
  }

  public void warn(String message, Supplier<Map<String, ?>> context) {
    if (!log.isWarnEnabled()) {
      return;
    }
    this.context(context.get());
    this.log.warn(message);
    this.clear();
  }

  public void error(String message) {
    if (!log.isErrorEnabled()) {
      return;
    }
    this.log.error(message);
  }

  public void error(String message, Map<String, ?> context) {
    if (!log.isErrorEnabled()) {
      return;
    }
    this.context(context);
    this.log.error(message);
    this.clear();
  }

  public void error(String message, Supplier<Map<String, ?>> context) {
    if (!log.isErrorEnabled()) {
      return;
    }
    this.context(context.get());
    this.log.error(message);
    this.clear();
  }

  public void error(String message, Exception exception) {
    Arrays.asList(exception.getStackTrace())
      .stream()
      .filter(
        element ->
          element
            .getClassName()
            .startsWith("io.github.opendonationassistant") &&
          !"io.github.opendonationassistant.commons.AbstractExceptionHandler".equals(
              element.getClassName()
            )
      )
      .findFirst()
      .ifPresentOrElse(
        element -> putMDC(element, exception),
        () -> putMDC(exception.getStackTrace()[0], exception)
      );
    log.error(message);
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
