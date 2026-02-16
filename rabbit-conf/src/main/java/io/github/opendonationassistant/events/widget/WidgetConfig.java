package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.ToString;
import io.micronaut.core.util.StringUtils;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Optional;

@Serdeable
public record WidgetConfig(List<WidgetProperty> properties) {
  public Optional<WidgetProperty> getProperty(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return properties()
      .stream()
      .filter(prop -> name.equals(prop.name()))
      .findFirst();
  }

  public <T> Optional<T> getValue(String name) {
    return this.getProperty(name).map(it -> (T) it);
  }

  @Override
  public String toString() {
    return ToString.asJson(this);
  }
}
