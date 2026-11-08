package io.github.opendonationassistant.commons.problem;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.zalando.problem.Status;

class ApiProblemTest {

  private static final String CODE = "internal_error";
  private static final String TITLE = "Internal Server Error";
  private static final String DETAIL = "The request could not be completed. Report the request id: ";
  private static final String REQUEST_ID = "request-1";
  private static final String INSTANCE = "/recipients/settings";

  @Test
  void buildsClassifiedProblem() {
    var problem = ApiProblem.of(
      Status.INTERNAL_SERVER_ERROR,
      CODE,
      TITLE,
      DETAIL + REQUEST_ID,
      INSTANCE,
      REQUEST_ID
    );

    assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
    assertEquals(URI.create("/problems/internal_error"), problem.getType());
    assertEquals(TITLE, problem.getTitle());
    assertEquals(DETAIL + REQUEST_ID, problem.getDetail());
    assertEquals(CODE, problem.getParameters().get("code"));
    assertEquals(REQUEST_ID, problem.getParameters().get("id"));
    assertEquals(URI.create(INSTANCE), problem.getInstance());
  }

  @Test
  void nullStatusIsRejected() {
    assertThrows(NullPointerException.class, () ->
      ApiProblem.of(null, CODE, TITLE, DETAIL + REQUEST_ID, INSTANCE, REQUEST_ID)
    );
  }

  @Test
  void nullInstanceDoesNotThrowAndLeavesInstanceNull() {
    var problem = assertDoesNotThrow(() ->
      ApiProblem.of(
        Status.INTERNAL_SERVER_ERROR,
        CODE,
        TITLE,
        DETAIL + REQUEST_ID,
        null,
        REQUEST_ID
      )
    );

    assertNull(problem.getInstance());
    assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
  }

  @Test
  void blankInstanceDoesNotThrowAndLeavesInstanceNull() {
    var problem = assertDoesNotThrow(() ->
      ApiProblem.of(
        Status.INTERNAL_SERVER_ERROR,
        CODE,
        TITLE,
        DETAIL + REQUEST_ID,
        "   ",
        REQUEST_ID
      )
    );

    assertNull(problem.getInstance());
    assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
  }

  @Test
  void invalidUriInstanceDoesNotThrowAndLeavesInstanceNull() {
    var problem = assertDoesNotThrow(() ->
      ApiProblem.of(
        Status.INTERNAL_SERVER_ERROR,
        CODE,
        TITLE,
        DETAIL + REQUEST_ID,
        "not a valid uri",
        REQUEST_ID
      )
    );

    assertNull(problem.getInstance());
    assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
  }
}
