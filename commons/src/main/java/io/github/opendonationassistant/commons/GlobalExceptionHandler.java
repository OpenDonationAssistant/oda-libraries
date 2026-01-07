package io.github.opendonationassistant.commons;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import org.zalando.problem.Problem;

@Singleton
public class GlobalExceptionHandler
  extends AbstractExceptionHandler
  implements ExceptionHandler<RuntimeException, HttpResponse<Problem>> {

  @Override
  public HttpResponse<Problem> handle(
    @SuppressWarnings("rawtypes") HttpRequest request,
    RuntimeException exception
  ) {
    log(exception);

    return HttpResponse.serverError(
      Problem.builder()
        .withTitle("Internal Server Error")
        .build()
    );
  }
}
