package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.events.HasSource;
import io.micronaut.serde.annotation.Serdeable;
import org.jspecify.annotations.Nullable;

@Serdeable
public record WidgetChangedEvent(
  String type,
  Widget widget,
  String source,
  @Nullable String originId
)
  implements HasSource {}
