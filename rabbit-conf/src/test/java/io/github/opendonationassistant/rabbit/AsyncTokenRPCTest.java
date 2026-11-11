package io.github.opendonationassistant.rabbit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.opendonationassistant.commons.BlockingExecutor;
import io.github.opendonationassistant.rabbit.TokenRPC.TokenRequest;
import io.github.opendonationassistant.rabbit.TokenRPC.TokenResponse;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class AsyncTokenRPCTest {

  private static final TokenRequest REQUEST = new TokenRequest("rec", "rt-1");

  private final ExecutorService executor =
    Executors.newVirtualThreadPerTaskExecutor();
  private final TokenRPC client = mock(TokenRPC.class);
  private final AsyncTokenRPC asyncClient = new AsyncTokenRPC(
    client,
    new BlockingExecutor(executor)
  );

  @AfterEach
  public void shutdown() {
    executor.close();
  }

  @Test
  public void tokenDelegatesTheRequest() {
    when(client.token(REQUEST)).thenReturn(new TokenResponse("token", "ok"));

    var response = asyncClient.token(REQUEST).join();

    verify(client).token(REQUEST);
    assertEquals("token", response.token());
    assertEquals("ok", response.message());
  }

  @Test
  public void tokenCompletesWithANullResponse() {
    when(client.token(REQUEST)).thenReturn(null);

    var response = asyncClient.token(REQUEST).join();

    verify(client).token(REQUEST);
    assertNull(response);
  }

  @Test
  public void tokenWrapsRuntimeExceptions() {
    var failure = new IllegalStateException("boom");
    doThrow(failure).when(client).token(REQUEST);

    var error = assertThrows(
      CompletionException.class,
      () -> asyncClient.token(REQUEST).join()
    );

    assertEquals(failure, unwrap(error));
  }

  @Test
  public void executesOnADifferentThread() {
    var caller = Thread.currentThread();
    var worker = new AtomicReference<Thread>();
    doAnswer(invocation -> {
      worker.set(Thread.currentThread());
      return new TokenResponse("token", "ok");
    })
      .when(client)
      .token(REQUEST);

    asyncClient.token(REQUEST).join();

    assertNotNull(worker.get());
    assertNotSame(caller, worker.get());
  }

  private static Throwable unwrap(Throwable error) {
    return error.getCause() == null ? error : unwrap(error.getCause());
  }
}
