package io.github.opendonationassistant.commons;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

class AbstractExceptionHandlerTest {

  private final Logger logger = (Logger) LoggerFactory.getLogger(
    AbstractExceptionHandler.class
  );
  private final ListAppender<ILoggingEvent> appender = new ListAppender<>();
  private final AbstractExceptionHandler handler = new AbstractExceptionHandler();

  @BeforeEach
  void attach() {
    appender.start();
    logger.addAppender(appender);
  }

  @AfterEach
  void detach() {
    logger.detachAppender(appender);
    MDC.clear();
  }

  private ILoggingEvent lastEvent() {
    assertFalse(appender.list.isEmpty(), "no log event was recorded");
    return appender.list.getLast();
  }

  private static Map<String, Object> parse(String json) {
    try {
      return ObjectMapper
        .getDefault()
        .readValue(json, Argument.mapOf(String.class, Object.class));
    } catch (Exception e) {
      throw new AssertionError("not valid JSON: " + json, e);
    }
  }

  private static String errorMdc(ILoggingEvent event) {
    var error = event.getMDCPropertyMap().get("error");
    assertNotNull(error, "MDC error was not set");
    return error;
  }

  @Test
  void messageIsServerErrorWithId() {
    handler.log(new IllegalStateException("kaboom"), "request-1");
    assertEquals("Server Error request-1", lastEvent().getFormattedMessage());
  }

  @Test
  void throwableIsPassedToTheBackend() {
    handler.log(new IllegalStateException("kaboom"), "request-1");
    assertNotNull(lastEvent().getThrowableProxy());
  }

  @Test
  void errorMdcIsValidJsonWhenMessageContainsDoubleQuote() {
    handler.log(new IllegalStateException("he said \"boom\""), "request-1");
    var error = errorMdc(lastEvent());
    var parsed = parse(error);
    assertEquals(Set.of("message", "location"), parsed.keySet(), error);
    assertTrue(
      parsed.get("message").toString().contains("he said \"boom\""),
      error
    );
    assertTrue(
      parsed.get("location").toString().contains("AbstractExceptionHandlerTest"),
      error
    );
  }

  @Test
  void unrelatedMdcKeySurvivesLogging() {
    MDC.put("traceId", "abc");
    handler.log(new IllegalStateException("kaboom"), "request-1");
    assertEquals("abc", MDC.get("traceId"));
  }

  @Test
  void emptyStackTraceFallsBackToUnknownLocation() {
    var exception = new EmptyStackTraceException("no frames");
    assertDoesNotThrow(() -> handler.log(exception, "request-1"));
    var error = errorMdc(lastEvent());
    var parsed = parse(error);
    assertEquals(Set.of("message", "location"), parsed.keySet(), error);
    assertEquals("Unknown location", parsed.get("location"), error);
  }

  private static class EmptyStackTraceException extends RuntimeException {

    EmptyStackTraceException(String message) {
      super(message);
    }

    @Override
    public StackTraceElement[] getStackTrace() {
      return new StackTraceElement[0];
    }
  }
}
