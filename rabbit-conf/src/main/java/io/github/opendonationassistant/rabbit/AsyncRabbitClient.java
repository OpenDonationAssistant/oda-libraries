package io.github.opendonationassistant.rabbit;

import io.github.opendonationassistant.commons.BlockingExecutor;
import io.github.opendonationassistant.events.HasRecipientId;
import java.util.concurrent.CompletableFuture;

public class AsyncRabbitClient {

  private final RabbitClient client;
  private final BlockingExecutor blockingExecutor;

  public AsyncRabbitClient(
    RabbitClient client,
    BlockingExecutor blockingExecutor
  ) {
    this.client = client;
    this.blockingExecutor = blockingExecutor;
  }

  public CompletableFuture<Void> sendCommand(Object payload) {
    return blockingExecutor.supply(() -> {
      client.sendCommand(payload);
      return null;
    });
  }

  public CompletableFuture<Void> sendEvent(HasRecipientId message) {
    return blockingExecutor.supply(() -> {
      client.sendEvent(message);
      return null;
    });
  }
}
