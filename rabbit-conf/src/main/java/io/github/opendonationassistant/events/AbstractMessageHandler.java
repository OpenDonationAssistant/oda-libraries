package io.github.opendonationassistant.events;

import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractMessageHandler<T> implements MessageHandler<T> {

  private final ObjectMapper mapper;

  public AbstractMessageHandler(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public void handle(byte[] message) throws IOException {
    T payload = mapper.readValue(message, payloadClass());
    if (payload == null) {
      return;
    }
    handle(payload);
  }

  public abstract void handle(T message) throws IOException;

  @Override
  public String type() {
    return payloadClass().getSimpleName();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Class<T> payloadClass() {
    var parameterizedType = (ParameterizedType) this.getClass()
      .getGenericSuperclass();
    return (Class<T>) parameterizedType.getActualTypeArguments()[0];
  }
}
