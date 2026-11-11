package io.github.opendonationassistant.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BlockingExecutorTest {

  private final ExecutorService executor =
    Executors.newVirtualThreadPerTaskExecutor();
  private final BlockingExecutor blockingExecutor = new BlockingExecutor(
    executor
  );

  @AfterEach
  public void shutdown() {
    executor.close();
  }

  @Test
  public void suppliesValuesOffTheCallingThread() {
    var caller = Thread.currentThread();

    var worker = blockingExecutor.supply(Thread::currentThread).join();

    assertNotSame(caller, worker);
  }

  @Test
  public void completesExceptionallyWhenTaskFails() {
    var result = blockingExecutor.supply(() -> {
      throw new IOException("boom");
    });

    var error = assertThrows(CompletionException.class, result::join);

    assertEquals(IOException.class, unwrap(error).getClass());
  }

  @Test
  public void passesCompletionExceptionsThrough() {
    var failure = new CompletionException(new IllegalStateException("boom"));

    var result = blockingExecutor.supply(() -> {
      throw failure;
    });

    var error = assertThrows(CompletionException.class, result::join);

    assertSame(failure, error);
  }

  private static Throwable unwrap(Throwable error) {
    return error.getCause() == null ? error : recurse(error.getCause());
  }

  private static Throwable recurse(Throwable error) {
    return error.getCause() == null ? error : recurse(error.getCause());
  }
}
