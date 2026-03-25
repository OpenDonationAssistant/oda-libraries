package io.github.opendonationassistant.events;

import org.jspecify.annotations.Nullable;

public interface HasSource {
  String source();

  @Nullable
  String originId();
}
