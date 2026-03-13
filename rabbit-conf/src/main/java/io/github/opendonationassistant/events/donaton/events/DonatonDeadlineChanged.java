package io.github.opendonationassistant.events.donaton.events;

import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

@Serdeable
public record DonatonDeadlineChanged(
  @Nullable String source,
  @Nullable String originId,
  String donationId,
  Instant before,
  Instant after
) {}
