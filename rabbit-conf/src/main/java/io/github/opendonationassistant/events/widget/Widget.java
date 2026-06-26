package io.github.opendonationassistant.events.widget;

import io.micronaut.core.util.StringUtils;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

@Serdeable
public record Widget(
  String id,
  String type,
  Integer sortOrder,
  String name,
  Boolean enabled,
  String ownerId,
  Boolean deleted,
  WidgetConfig config
) {

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
  }

  @Serdeable
  public record WidgetProperty(
    String name,
    String displayName,
    String type,
    @Nullable Object value
  ) {}
}
