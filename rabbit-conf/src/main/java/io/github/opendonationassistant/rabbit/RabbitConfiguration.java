package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RabbitConfiguration extends ChannelInitializer {

  private ODALogger log = new ODALogger(this);

  // prettier-ignore ON
  @Override
  public void initialize(Channel channel, String name) throws IOException {
    log.info("Initializing all exchanges in RabbitMQ", Map.of());
    channel.exchangeDeclare(Exchange.Configs.WIDGETS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.PAYMENTS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.GOALS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.ACTIONS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.WIDGETS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.VOTING, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.TWITCH, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.HISTORY, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.MEDIA, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.REEL, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.FILES, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.DONATON, BuiltinExchangeType.TOPIC);

    Mapping.getQueues().forEach(queue -> {
      declareAndBind(channel, queue);
    });
  }

  // prettier-ignore OFF
  private void declareAndBind(Channel channel, QueueParams params) {
    try {
      channel.queueDeclare(
        params.getQueueName(),
        true,
        false,
        false,
        new HashMap<>()
      );
      channel.queueBind(
        params.getQueueName(),
        params.getExchangeName(),
        params.getRoutingKey()
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
