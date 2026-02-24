package io.github.opendonationassistant.commons;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.problem.ThrowableProblemHandler;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.UUID;
import org.zalando.problem.Problem;

@Singleton
public class GlobalExceptionHandler
  extends AbstractExceptionHandler
  implements ExceptionHandler<RuntimeException, HttpResponse<?>> {

  private final ThrowableProblemHandler delegate;

  @Inject
  public GlobalExceptionHandler(ThrowableProblemHandler delegate) {
    super();
    this.delegate = delegate;
  }

  @Override
  public HttpResponse<?> handle(
    @SuppressWarnings("rawtypes") HttpRequest request,
    RuntimeException exception
  ) {
    var id = UUID.randomUUID().toString();
    log(exception, id);

    return delegate.handle(
      request,
      Problem.builder().withTitle("Server Error").with("id", id).build()
    );
  }
}
