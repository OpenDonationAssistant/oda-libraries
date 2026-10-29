package io.github.opendonationassistant.commons.logging;

import io.github.opendonationassistant.commons.ToString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ODALogger {

  static final String CONTEXT_KEY = "context";
  static final String ERROR_KEY = "error";

  private static final String ODA_PACKAGE = "io.github.opendonationassistant";
  private static final String EXCEPTION_HANDLER_CLASS =
    "io.github.opendonationassistant.commons.AbstractExceptionHandler";

  private final org.slf4j.Logger log;

  public ODALogger(Object context) {
    this.log = LoggerFactory.getLogger(context.getClass());
  }

  public ODALogger(Class<?> className) {
    this.log = LoggerFactory.getLogger(className);
  }

  public void context(Map<String, ?> context) {
    MDC.put(CONTEXT_KEY, ToString.asJson(context));
  }

  public void clear() {
    MDC.remove(CONTEXT_KEY);
  }

  public void info(String message) {
    log.info(message);
  }

  public void info(String message, Map<String, ?> context) {
    info(message, () -> context);
  }

  public void info(String message, Supplier<Map<String, ?>> context) {
    if (!log.isInfoEnabled()) {
      return;
    }
    withContext(context, () -> log.info(message));
  }

  public void debug(String message) {
    log.debug(message);
  }

  public void debug(String message, Map<String, ?> context) {
    debug(message, () -> context);
  }

  public void debug(String message, Supplier<Map<String, ?>> context) {
    if (!log.isDebugEnabled()) {
      return;
    }
    withContext(context, () -> log.debug(message));
  }

  public void warn(String message) {
    log.warn(message);
  }

  public void warn(String message, Map<String, ?> context) {
    warn(message, () -> context);
  }

  public void warn(String message, Supplier<Map<String, ?>> context) {
    if (!log.isWarnEnabled()) {
      return;
    }
    withContext(context, () -> log.warn(message));
  }

  public void error(String message) {
    log.error(message);
  }

  public void error(String message, Map<String, ?> context) {
    error(message, () -> context);
  }

  public void error(String message, Supplier<Map<String, ?>> context) {
    if (!log.isErrorEnabled()) {
      return;
    }
    withContext(context, () -> log.error(message));
  }

  public void error(String message, Exception exception) {
    logError(message, exception, Map.of());
  }

  public void error(String message, String recipientId, Exception exception) {
    logError(message, exception, Map.of("recipientId", recipientId));
  }

  private void logError(String message, Exception exception, Map<String, ?> context) {
    restore(
      ERROR_KEY,
      describeError(exception),
      () -> {
        if (context.isEmpty()) {
          log.error(message, exception);
        } else {
          withContext(() -> context, () -> log.error(message, exception));
        }
      }
    );
  }

  private String describeError(Exception exception) {
    var messages = String.join("; ", collectErrorMessages(exception));
    var location = findRelevantFrame(exception)
      .map(element ->
        "%s:%s".formatted(
            Optional.ofNullable(element.getClassName()).orElse("Unknown class"),
            element.getLineNumber()
          )
      )
      .orElse("Unknown location");
    var payload = new LinkedHashMap<String, String>();
    payload.put("message", messages);
    payload.put("location", location);
    return ToString.asJson(payload);
  }

  private static Optional<StackTraceElement> findRelevantFrame(Exception exception) {
    return Arrays.stream(exception.getStackTrace())
      .filter(element -> element.getClassName().startsWith(ODA_PACKAGE))
      .filter(element -> !EXCEPTION_HANDLER_CLASS.equals(element.getClassName()))
      .findFirst()
      .or(() -> Arrays.stream(exception.getStackTrace()).findFirst());
  }

  private static void withContext(Supplier<Map<String, ?>> context, Runnable action) {
    restore(CONTEXT_KEY, ToString.asJson(context.get()), action);
  }

  private static void restore(String key, String value, Runnable action) {
    var previous = MDC.get(key);
    MDC.put(key, value);
    try {
      action.run();
    } finally {
      if (previous == null) {
        MDC.remove(key);
      } else {
        MDC.put(key, previous);
      }
    }
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
