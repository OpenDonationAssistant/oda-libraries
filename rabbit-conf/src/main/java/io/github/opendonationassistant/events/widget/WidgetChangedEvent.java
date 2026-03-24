package io.github.opendonationassistant.events.widget;

import org.jspecify.annotations.Nullable;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record WidgetChangedEvent(
  String type,
  Widget widget,
  String source,
  @Nullable String originId
) {}
