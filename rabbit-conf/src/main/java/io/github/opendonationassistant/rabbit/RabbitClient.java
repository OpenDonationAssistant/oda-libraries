package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.events.HasRecipientId;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.serde.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RabbitClient {

  private final ODALogger log = new ODALogger(this);
  private final String exchange;
  private final Channel channel;
  private final ObjectMapper mapper;

  public RabbitClient(
    Channel channel,
    ObjectMapper mapper,
    @Binding String exchange
  ) {
    this.channel = channel;
    this.exchange = exchange;
    this.mapper = mapper;
  }

  public void sendCommand(Object payload) {
    var type = payload.getClass().getSimpleName();
    Map<String, Object> headers = new HashMap<>();
    headers.put("type", type);

    try {
      log.debug(
        "Sending command",
        Map.of("exchange", exchange, "type", type, "payload", payload)
      );
      sendMessage(headers, "command", mapper.writeValueAsBytes(payload));
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  public void sendEvent(HasRecipientId message) throws IOException {
    var type = message.getClass().getSimpleName();
    Map<String, Object> headers = new HashMap<>();
    headers.put("type", type);
    headers.put("recipientId", message.recipientId());

    var key = "event.%s".formatted(type);

    try {
      log.debug(
        "Sending event",
        Map.of(
          "exchange",
          exchange,
          "type",
          type,
          "key",
          key,
          "payload",
          message
        )
      );
      sendMessage(headers, key, mapper.writeValueAsBytes(message));
    } catch (Exception e) {
      log.error("Serialization error", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  private void sendMessage(
    Map<String, Object> headers,
    String key,
    byte[] message
  ) throws IOException {
    AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
      .headers(headers)
      .build();

    channel.basicPublish(exchange, key, properties, message);
  }
}

