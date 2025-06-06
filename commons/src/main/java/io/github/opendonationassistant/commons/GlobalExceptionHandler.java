package io.github.opendonationassistant.commons;

import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.zalando.problem.Problem;

@Singleton
public class GlobalExceptionHandler
  implements ExceptionHandler<RuntimeException, HttpResponse<Problem>> {

  private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @Override
  public HttpResponse<Problem> handle(
    HttpRequest request,
    RuntimeException exception
  ) {
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

    return HttpResponse.serverError(
      Problem.builder().withTitle("Internal Server Error").build()
    );
  }

  private List<String> collectErrorMessages(Throwable exception) {
    var messages = new ArrayList<String>();
    var cause = exception;
    while (cause != null) {
      Optional.ofNullable(cause.getMessage()).ifPresent(messages::add);
      cause = cause.getCause();
    }
    return messages;
  }
}
