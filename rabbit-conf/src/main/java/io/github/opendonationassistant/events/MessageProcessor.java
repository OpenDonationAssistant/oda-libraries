package io.github.opendonationassistant.events;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class MessageProcessor {

  private List<MessageHandler> handlers;

  @Inject
  public MessageProcessor(List<MessageHandler> handlers) {
    this.handlers = handlers;
  }

  public void process(String type, byte[] message) {
    handlers
      .stream()
      .filter(handler -> handler.type().equals(type))
      .findFirst()
      .ifPresent(handler -> handler.handle(message));
  }
}
