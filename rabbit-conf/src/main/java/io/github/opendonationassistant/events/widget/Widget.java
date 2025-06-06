package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.ToString;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record Widget(
  String id,
  String type,
  Integer sortOrder,
  String name,
  String ownerId,
  WidgetConfig config
) {
  @Override
  public String toString() {
    return ToString.asJson(this);
  }
}
