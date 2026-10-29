package io.github.opendonationassistant.commons.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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

class ODALoggerTest {

  private final Logger logger = (Logger) LoggerFactory.getLogger(
    ODALoggerTest.class
  );
  private final ListAppender<ILoggingEvent> appender = new ListAppender<>();
  private final ODALogger log = new ODALogger(ODALoggerTest.class);

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

  @Test
  void contextIsRestoredAfterLogging() {
    MDC.put("context", "{\"outer\":true}");
    log.info("message", Map.of("inner", true));
    assertEquals("{\"outer\":true}", MDC.get("context"));
  }

  @Test
  void contextIsClearedWhenAbsentBeforeLogging() {
    log.info("message", Map.of("inner", true));
    assertNull(MDC.get("context"));
  }

  @Test
  void unrelatedMdcEntriesSurviveErrorLogging() {
    MDC.put("traceId", "abc");
    log.error("boom", new IllegalStateException("kaboom"));
    assertEquals("abc", MDC.get("traceId"));
  }

  @Test
  void errorMdcIsSingleValidJsonObject() {
    log.error("boom", new IllegalStateException("kaboom"));
    var error = lastEvent().getMDCPropertyMap().get("error");
    assertNotNull(error);
    var parsed = parse(error);
    assertEquals(Set.of("message", "location"), parsed.keySet(), error);
    assertTrue(parsed.get("message").toString().contains("kaboom"), error);
    assertTrue(parsed.get("location").toString().contains("ODALoggerTest"), error);
  }

  @Test
  void errorMdcIncludesCauseChain() {
    log.error("boom", new IllegalStateException("outer", new RuntimeException("inner")));
    var message = parse(lastEvent().getMDCPropertyMap().get("error"))
      .get("message")
      .toString();
    assertTrue(message.contains("outer"), message);
    assertTrue(message.contains("inner"), message);
  }

  @Test
  void recipientIdIsKeptInContext() {
    log.error("boom", "recipient-1", new IllegalStateException("kaboom"));
    var context = lastEvent().getMDCPropertyMap().get("context");
    assertNotNull(context);
    assertEquals("recipient-1", parse(context).get("recipientId"));
  }

  @Test
  void plainErrorDoesNotAddEmptyContext() {
    log.error("boom", new IllegalStateException("kaboom"));
    assertNull(lastEvent().getMDCPropertyMap().get("context"));
  }

  @Test
  void throwableIsHandedToTheBackend() {
    log.error("boom", new IllegalStateException("kaboom"));
    assertNotNull(lastEvent().getThrowableProxy());
  }
}
