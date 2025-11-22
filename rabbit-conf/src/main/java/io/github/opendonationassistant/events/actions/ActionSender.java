package io.github.opendonationassistant.events.actions;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@RabbitClient(Exchange.ACTIONS)
public interface ActionSender {
  final ODALogger log = new ODALogger(ActionSender.class);

  CompletableFuture<Void> internalSend(
    @Binding String binding,
    List<Action> actions
  );

  default CompletableFuture<Void> publishCreatedActions(List<Action> actions) {
    log.info("Send Actions", Map.of("actions", actions));
    return internalSend(Key.FINALIZED, actions).thenRun(() ->
      log.debug("Actions Sent", Map.of("actions", actions))
    );
  }

  @Serdeable
  public static record Action(
    String id,
    @NonNull String recipientId,
    @Nullable String category,
    @NonNull String provider,
    @NonNull String name,
    @NonNull Amount amount,
    @Nullable String game,
    Boolean enabled,
    @NonNull Map<String, Object> payload
  ) {}
}
