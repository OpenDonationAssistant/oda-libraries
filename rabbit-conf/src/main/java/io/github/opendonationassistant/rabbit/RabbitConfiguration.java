package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;

@Singleton
public class RabbitConfiguration extends ChannelInitializer {

  @Override
  public void initialize(Channel channel, String name) throws IOException {
    channel.exchangeDeclare(Exchange.COMMANDS, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(
      Exchange.Configs.WIDGETS,
      BuiltinExchangeType.TOPIC
    );
    channel.exchangeDeclare(Exchange.PAYMENTS, BuiltinExchangeType.TOPIC);
    Mapping.QUEUES.forEach(queue -> {
      declareAndBind(channel, queue);
    });
  }

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
