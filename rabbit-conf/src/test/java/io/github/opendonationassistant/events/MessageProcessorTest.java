package io.github.opendonationassistant.events;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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

  private List<MessageHandler<?>> handlers = List.of(handler);
  private MessageProcessor processor = new MessageProcessor(handlers);
  private RabbitAcknowledgement ack = mock(RabbitAcknowledgement.class);
  private byte[] message = "test message".getBytes(StandardCharsets.UTF_8);

  @BeforeEach
  void setUp() {
    when(handler.type()).thenReturn("testType");
    when(handler.payloadClass()).thenReturn(String.class);

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
}
