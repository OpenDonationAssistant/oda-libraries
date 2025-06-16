package io.github.opendonationassistant.events.command;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Command(String id, String command) {}
