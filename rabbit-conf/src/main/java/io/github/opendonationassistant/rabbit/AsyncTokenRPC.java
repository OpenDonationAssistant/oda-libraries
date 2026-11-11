package io.github.opendonationassistant.rabbit;

import io.github.opendonationassistant.commons.BlockingExecutor;
import io.github.opendonationassistant.rabbit.TokenRPC.TokenRequest;
import io.github.opendonationassistant.rabbit.TokenRPC.TokenResponse;
import java.util.concurrent.CompletableFuture;

public class AsyncTokenRPC {

  private final TokenRPC client;
  private final BlockingExecutor blockingExecutor;

  public AsyncTokenRPC(TokenRPC client, BlockingExecutor blockingExecutor) {
    this.client = client;
    this.blockingExecutor = blockingExecutor;
  }

  public CompletableFuture<TokenResponse> token(TokenRequest request) {
    return blockingExecutor.supply(() -> client.token(request));
  }
}
