package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.ToString;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record WidgetUpdateCommand(String id, WidgetConfig patch) {
  @Override
  public String toString() {
    return ToString.asJson(this);
  }
}
