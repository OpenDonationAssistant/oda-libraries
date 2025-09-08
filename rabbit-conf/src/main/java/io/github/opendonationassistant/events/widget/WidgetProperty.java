package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.ToString;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nullable;

@Serdeable
public record WidgetProperty(
  String name,
  String displayName,
  String type,
  @Nullable Object value
) {
  @Override
  public String toString() {
    return ToString.asJson(this);
  }
}
