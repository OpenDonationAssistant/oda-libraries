package io.github.opendonationassistant.events.widget;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record WidgetChangedEvent(String type, Widget widget) {}
