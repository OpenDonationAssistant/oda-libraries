package io.github.opendonationassistant.events.history;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record TargetGoal(String goalId, String goalTitle) {}
