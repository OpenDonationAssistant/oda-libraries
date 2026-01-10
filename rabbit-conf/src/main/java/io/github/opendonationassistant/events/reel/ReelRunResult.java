package io.github.opendonationassistant.events.reel;

import org.jspecify.annotations.Nullable;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record ReelRunResult(
  String recipientId,
  @Nullable String paymentId,
  String widgetId
) {}
