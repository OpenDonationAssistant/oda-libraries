package io.github.opendonationassistant.events;

import java.io.IOException;

public interface MessageHandler {
  String type();
  void handle(byte[] message) throws IOException;
}
