package io.github.opendonationassistant.events;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.micronaut.rabbitmq.bind.RabbitAcknowledgement;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageProcessorTest {

  @SuppressWarnings("unchecked")
  private MessageHandler<String> handler = (MessageHandler<String>) mock(
    MessageHandler.class
  );

  @SuppressWarnings("unchecked")
  private MessageHandler<String> handler2 = (MessageHandler<String>) mock(
    MessageHandler.class
  );

  @SuppressWarnings("unchecked")
  private MessageHandler<String> handler3 = (MessageHandler<String>) mock(
    MessageHandler.class
  );

  private List<MessageHandler<?>> handlers = List.of(
    handler,
    handler2,
    handler3
  );
  private MessageProcessor processor = new MessageProcessor(handlers);
  private RabbitAcknowledgement ack = mock(RabbitAcknowledgement.class);
  private byte[] message = "test message".getBytes(StandardCharsets.UTF_8);

  @BeforeEach
  void setUp() {
    when(handler.type()).thenReturn("testType");
    when(handler.payloadClass()).thenReturn(String.class);

    when(handler2.type()).thenReturn("testType2");
    when(handler2.payloadClass()).thenReturn(String.class);

    when(handler3.type()).thenReturn("testType3");
    when(handler3.payloadClass()).thenReturn(String.class);

    doNothing().when(ack).ack();
  }

  @Test
  public void testProcessMessageWithMatchingHandler() throws IOException {
    processor.process("testType", message, ack);

    verify(handler).handle(eq(message));
    verify(ack).ack();
  }

  @Test
  public void testProcessMessageWithNoMatchingHandler() throws IOException {
    processor.process("nonExistentType", message, ack);

    verify(handler, never()).handle(any());
    verify(handler2, never()).handle(any());
    verify(handler3, never()).handle(any());
    verify(ack, never()).ack();
  }

  @Test
  public void testNotAckWhenErrorOccurs() throws IOException {
    doThrow(new IOException("test error"))
      .when(handler)
      .handle(any(byte[].class));

    processor.process("testType", message, ack);

    verify(ack, never()).ack();
  }

  @Test
  public void testCallsOnlyMatchingHandler() throws IOException {
    processor.process("testType2", message, ack);

    verify(handler, never()).handle(any());
    verify(handler2).handle(eq(message));
    verify(handler3, never()).handle(any());
    verify(ack).ack();
  }

  @Test
  public void testWithMultipleHandlersOfSameType() throws IOException {
    @SuppressWarnings("unchecked")
    MessageHandler<String> sameHandler = (MessageHandler<String>) mock(
      MessageHandler.class
    );
    when(sameHandler.type()).thenReturn("testType");
    when(sameHandler.payloadClass()).thenReturn(String.class);
    doNothing().when(sameHandler).handle(any());

    List<MessageHandler<?>> handlersWithDuplicate = List.of(
      handler,
      sameHandler,
      handler2,
      handler3
    );

    MessageProcessor processorWithDups = new MessageProcessor(
      handlersWithDuplicate
    );

    processorWithDups.process("testType", message, ack);

    verify(handler).handle(eq(message));
    verify(sameHandler).handle(eq(message));
    verify(handler2, never()).handle(any());
    verify(handler3, never()).handle(any());
    verify(ack, times(2)).ack();
  }
}
