package io.github.opendonationassistant.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class AbstractMessageHandlerTest {

  @Test
  public void testGettingClass() {
    var handler = new TestMessageHandler(ObjectMapper.getDefault());
    assertEquals(String.class, handler.payloadClass());
    assertEquals("String", handler.type());
  }

  public static class TestMessageHandler
    extends AbstractMessageHandler<String> {

    public TestMessageHandler(ObjectMapper mapper) {
      super(mapper);
    }

    @Override
    public void handle(String message) throws IOException {}
  }
}
