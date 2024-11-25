package io.github.opendonationassistant.events.widget;

import java.util.List;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class WidgetConfig {

  private List<WidgetProperty> properties;

  public List<WidgetProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<WidgetProperty> properties) {
    this.properties = properties;
  }

  @Override
  public String toString() {
    return "{\"_type\"=\"WidgetConfig\",\"properties\"=\"" + properties + "}";
  }
}
