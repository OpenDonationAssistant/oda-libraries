package io.github.opendonationassistant.events;

public interface MessageHandler {
  String type();
  void handle(byte[] message);
}
