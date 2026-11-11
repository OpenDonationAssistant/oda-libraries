package io.github.opendonationassistant.commons;

import io.micronaut.scheduling.TaskExecutors;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;

@Singleton
public class BlockingExecutor {

  private final ExecutorService executor;

  @Inject
  public BlockingExecutor(
    @Named(TaskExecutors.BLOCKING) ExecutorService executor
  ) {
    this.executor = executor;
  }

  public <T> CompletableFuture<T> supply(Callable<T> callable) {
    return CompletableFuture.supplyAsync(
      () -> {
        try {
          return callable.call();
        } catch (CompletionException e) {
          throw e;
        } catch (Exception e) {
          throw new CompletionException(e);
        }
      },
      executor
    );
  }
}
