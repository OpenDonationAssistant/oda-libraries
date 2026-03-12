package io.github.opendonationassistant.events;

import java.io.IOException;

public interface MessageHandler<T> {
  String type();
  void handle(byte[] message) throws IOException;
  Class<T> payloadClass();
}
