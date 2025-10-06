package io.github.opendonationassistant.events.config;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Map;

public interface ConfigCommand {
  @Serdeable
  public static record PutKeyValue(
    String recipientId,
    String name,
    String key,
    Object value
  ) {}

  @Serdeable
  public static record UpsertAction(
    String recipientId,
    String id,
    String name,
    Amount price,
    String category,
    String game,
    Map<String, Object> payload
  ) {}

  @Serdeable
  public static record DeleteAction(
    String recipientId,
    String id
  ) {}
}
