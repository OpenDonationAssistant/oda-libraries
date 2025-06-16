package io.github.opendonationassistant.events.alerts;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record AlertCommand(String paymentId, String message) {}
