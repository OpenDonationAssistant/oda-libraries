package io.github.opendonationassistant.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpRequestWrapper;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import io.micronaut.problem.ThrowableProblemHandler;
import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

class GlobalExceptionHandlerTest {

  private final Logger logger = (Logger) LoggerFactory.getLogger(
    AbstractExceptionHandler.class
  );
  private final ListAppender<ILoggingEvent> appender = new ListAppender<>();
  private final AtomicReference<Problem> capturedProblem =
    new AtomicReference<>();

  @BeforeEach
  void attach() {
    appender.start();
    logger.addAppender(appender);
  }

  @AfterEach
  void detach() {
    logger.detachAppender(appender);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  private final ErrorResponseProcessor responseProcessor = (
    context,
    response
  ) -> {
    capturedProblem.set((Problem) context.getRootCause().orElse(null));
    return response;
  };

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler(
    new ThrowableProblemHandler(responseProcessor)
  );

  private ILoggingEvent lastLoggedEvent() {
    assertFalse(appender.list.isEmpty(), "no log event was recorded");
    return appender.list.getLast();
  }

  @Test
  void runtimeExceptionBecomesInternalServerErrorProblem() {
    var response = handler.handle(
      HttpRequest.GET("/recipients/settings"),
      new IllegalStateException("boom")
    );

    var problem = capturedProblem.get();
    assertNotNull(problem, "ThrowableProblemHandler did not process a problem");
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
    assertEquals("Internal Server Error", problem.getTitle());
    assertEquals(URI.create("/problems/internal_error"), problem.getType());
    assertEquals("internal_error", problem.getParameters().get("code"));
    var id = problem.getParameters().get("id");
    assertNotNull(id, "the generated request id must be reported");
    assertFalse(id.toString().isBlank(), "the request id must be non-blank");
    var detail = problem.getDetail();
    assertNotNull(detail);
    assertTrue(
      detail.contains(id.toString()),
      "detail must mention the request id: " + detail
    );
    assertTrue(
      detail.endsWith(id.toString()),
      "detail must end with the request id: " + detail
    );
    assertFalse(
      detail.contains("boom"),
      "detail must not leak the exception message: " + detail
    );
    assertEquals(
      "Server Error " + id,
      lastLoggedEvent().getFormattedMessage(),
      "the logged message must use the same request id as the response"
    );
    assertEquals(
      URI.create("/recipients/settings"),
      problem.getInstance(),
      "instance must be the request path"
    );
  }

  @Test
  void missingPathStillProducesInternalServerError() {
    var request = new HttpRequestWrapper<Object>(HttpRequest.GET("/ignored")) {
      @Override
      public String getPath() {
        return null;
      }
    };

    var response = handler.handle(request, new IllegalStateException("boom"));

    var problem = capturedProblem.get();
    assertNotNull(problem, "ThrowableProblemHandler did not process a problem");
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
    assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
    assertNull(problem.getInstance());
  }
}
