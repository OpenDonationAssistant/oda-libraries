package io.github.opendonationassistant.commons.problem;

import java.net.URI;
import java.util.Objects;
import org.jspecify.annotations.Nullable;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

public final class ApiProblem {

  /** Relative type reference, resolved against the request URI as allowed by RFC 7807. */
  private static final String TYPE_PREFIX = "/problems/";

  private ApiProblem() {}

  public static ThrowableProblem of(
    Status status,
    String code,
    String title,
    String detail,
    @Nullable String instance,
    String requestId
  ) {
    Objects.requireNonNull(status, "status");
    var builder = Problem.builder()
      .withType(URI.create(TYPE_PREFIX + code))
      .withStatus(status)
      .withTitle(title)
      .withDetail(detail)
      .with("code", code)
      .with("id", requestId);
    var instanceUri = uri(instance);
    if (instanceUri != null) {
      builder.withInstance(instanceUri);
    }
    return builder.build();
  }

  private static @Nullable URI uri(@Nullable String value) {
    if (value == null || value.isBlank()) {
      return null;
    }
    try {
      return URI.create(value);
    } catch (IllegalArgumentException invalidUri) {
      return null;
    }
  }
}
