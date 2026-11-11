package io.github.opendonationassistant.rabbit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.github.opendonationassistant.commons.BlockingExecutor;
import io.github.opendonationassistant.events.HasRecipientId;
import java.io.IOException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class AsyncRabbitClientTest {

  private final ExecutorService executor =
    Executors.newVirtualThreadPerTaskExecutor();
  private final RabbitClient client = mock(RabbitClient.class);
  private final AsyncRabbitClient asyncClient = new AsyncRabbitClient(
    client,
    new BlockingExecutor(executor)
  );

  @AfterEach
  public void shutdown() {
    executor.close();
  }

  @Test
  public void sendCommandDelegatesThePayload() {
    var payload = new Object();

    asyncClient.sendCommand(payload).join();

    verify(client).sendCommand(payload);
  }

  @Test
  public void sendCommandWrapsRuntimeExceptions() {
    var payload = new Object();
    var failure = new IllegalStateException("boom");
    doThrow(failure).when(client).sendCommand(payload);

    var error = assertThrows(
      CompletionException.class,
      () -> asyncClient.sendCommand(payload).join()
    );

    assertEquals(failure, unwrap(error));
  }

  @Test
  public void sendEventDelegatesTheMessage() throws IOException {
    var event = mock(HasRecipientId.class);

    asyncClient.sendEvent(event).join();

    verify(client).sendEvent(event);
  }

  @Test
  public void sendEventWrapsRuntimeExceptions() throws IOException {
    var event = mock(HasRecipientId.class);
    var failure = new IllegalStateException("boom");
    doThrow(failure).when(client).sendEvent(event);

    var result = asyncClient.sendEvent(event);

    var error = assertThrows(CompletionException.class, result::join);
    assertEquals(failure, unwrap(error));
  }

  @Test
  public void sendEventWrapsCheckedExceptions() throws IOException {
    var event = mock(HasRecipientId.class);
    var failure = new IOException("channel closed");
    doThrow(failure).when(client).sendEvent(event);

    var error = assertThrows(
      CompletionException.class,
      () -> asyncClient.sendEvent(event).join()
    );

    assertEquals(failure, unwrap(error));
  }

  @Test
  public void executesOnADifferentThread() throws IOException {
    var event = mock(HasRecipientId.class);
    var caller = Thread.currentThread();
    var worker = new AtomicReference<Thread>();
    doAnswer(invocation -> {
      worker.set(Thread.currentThread());
      return null;
    })
      .when(client)
      .sendEvent(event);

    asyncClient.sendEvent(event).join();

    assertNotNull(worker.get());
    assertNotSame(caller, worker.get());
  }

  private static Throwable unwrap(Throwable error) {
    return error.getCause() == null ? error : unwrap(error.getCause());
  }
}
