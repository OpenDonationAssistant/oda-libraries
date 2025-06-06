package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.ToString;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record WidgetConfig(List<WidgetProperty> properties) {
  @Override
  public String toString() {
    return ToString.asJson(this);
  }
}
