package io.github.opendonationassistant;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Singleton
public class RabbitConfiguration extends ChannelInitializer {

  public static final String COMMANDS_EXCHANGE_NAME = "commands";
  public static final String HISTORY_COMMANDS_QUEUE_NAME = "commands.history";
  public static final String HISTORY_COMMANDS_ROUTING_KEY = "history";
  public static final String REEL_COMMANDS_QUEUE_NAME = "commands.reel";
  public static final String REEL_COMMANDS_ROUTING_KEY = "reel";

  public static final String AMQ_TOPIC = "amq.topic";
  public static final String PAYMENT_CONTRIBUTIONS_QUEUE_NAME =
    "payments_for_contributions";
  public static final String PAYMENT_REEL_QUEUE_NAME = "payments_for_reel";
  public static final String PAYMENT_GOAL_QUEUE_NAME = "payments_for_goal";
  public static final String PAYMENT_HISTORY_QUEUE_NAME = "payments_for_history";
  public static final String PAYMENTS_ROUTING_KEY = "payments";

  private final List<QueueParams> queues = Arrays.asList(
    new QueueParams[] {
      new QueueParams() {
        {
          setExchangeName(AMQ_TOPIC);
          setRoutingKey(PAYMENTS_ROUTING_KEY);
          setQueueName(PAYMENT_REEL_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(AMQ_TOPIC);
          setRoutingKey(PAYMENTS_ROUTING_KEY);
          setQueueName(PAYMENT_GOAL_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(AMQ_TOPIC);
          setRoutingKey(PAYMENTS_ROUTING_KEY);
          setQueueName(PAYMENT_CONTRIBUTIONS_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(AMQ_TOPIC);
          setRoutingKey(PAYMENTS_ROUTING_KEY);
          setQueueName(PAYMENT_HISTORY_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(COMMANDS_EXCHANGE_NAME);
          setRoutingKey(HISTORY_COMMANDS_ROUTING_KEY);
          setQueueName(HISTORY_COMMANDS_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(COMMANDS_EXCHANGE_NAME);
          setRoutingKey(REEL_COMMANDS_ROUTING_KEY);
          setQueueName(REEL_COMMANDS_QUEUE_NAME);
        }
      },
    }
  );

  @Override
  public void initialize(Channel channel, String name) throws IOException {
    channel.exchangeDeclare(COMMANDS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    queues.forEach(queue -> {
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

  static class QueueParams {

    private String queueName;
    private String exchangeName;
    private String routingKey;

    public String getQueueName() {
      return queueName;
    }

    public void setQueueName(String queueName) {
      this.queueName = queueName;
    }

    public String getExchangeName() {
      return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
      this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
      return routingKey;
    }

    public void setRoutingKey(String routingKey) {
      this.routingKey = routingKey;
    }
  }
}
