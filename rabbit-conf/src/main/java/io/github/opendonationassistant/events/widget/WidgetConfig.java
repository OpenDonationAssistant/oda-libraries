package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.ToString;
import io.micronaut.core.util.StringUtils;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Serdeable
public record WidgetConfig(@Nonnull List<WidgetProperty> properties) {
  public @Nonnull Optional<WidgetProperty> get(String name) {
    if (StringUtils.isEmpty(name)) {
      return Optional.empty();
    }
    return properties()
      .stream()
      .filter(prop -> name.equals(prop.name()))
      .findFirst();
  }

  @Override
  public String toString() {
    return ToString.asJson(this);
  }
}
