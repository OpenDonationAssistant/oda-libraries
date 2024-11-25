package io.github.opendonationassistant.events.widget;

import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class WidgetUpdateCommand {

  private String id;
  private WidgetConfig patch;

  public WidgetUpdateCommand(String id, WidgetConfig patch) {
    this.id = id;
    this.patch = patch;
  }

  public String getId() {
    return id;
  }

  public WidgetConfig getPatch() {
    return patch;
  }

  @Override
  public String toString() {
    try {
      return ObjectMapper.getDefault().writeValueAsString(this);
    } catch (Exception e) {
      return "Can't serialize WidgetUpdateCommand";
    }
  }
}
