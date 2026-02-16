package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;

@Singleton
public class RabbitConfiguration extends ChannelInitializer {

  // prettier-ignore ON
  @Override
  public void initialize(Channel channel, String name) throws IOException {
    channel.exchangeDeclare(Exchange.COMMANDS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.Configs.WIDGETS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.PAYMENTS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.EVENTS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.GOALS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.ACTIONS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.VOTING, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.TWITCH, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(Exchange.HISTORY, BuiltinExchangeType.TOPIC);

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
