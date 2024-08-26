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

  public static class Exchange {

    public static final String COMMANDS = "commands";
  }

  public static class Key {

    public static final String HISTORY = "history";
    public static final String CONFIG = "config";
    public static final String FILES = "files";
  }

  public static class Queue {

    public static final String COMMANDS_HISTORY = "commands.history";
    public static final String COMMANDS_CONFIG = "commands.config";
    public static final String COMMANDS_FILES = "commands.files";
  }

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
  public static final String PAYMENT_HISTORY_QUEUE_NAME =
    "payments_for_history";
  public static final String PAYMENTS_ROUTING_KEY = "payments";

  public static final String WIDGET_CONFIGS_EXCHANGE_NAME = "changes.widgets";
  public static final String REEL_CONFIG_QUEUE_NAME = "config.reel";
  public static final String REEL_CONFIG_ROUTING_KEY = "reel";
  public static final String GOALS_CONFIG_QUEUE_NAME = "config.goals";
  public static final String GOALS_CONFIG_ROUTING_KEY = "donationgoal";

  public static final String PAYMENTS_EXCHANGE_NAME = "payments";
  public static final String PAYMENT_CONTRIBUTIONS_ROUTING_KEY =
    "contributions";
  public static final String PAYMENT_REEL_ROUTING_KEY = "reel";
  public static final String PAYMENT_GOAL_ROUTING_KEY = "goal";

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
      new QueueParams() {
        {
          setExchangeName(Exchange.COMMANDS);
          setRoutingKey(Key.CONFIG);
          setQueueName(Queue.COMMANDS_CONFIG);
        }
      },
      new QueueParams() {
        {
          setExchangeName(Exchange.COMMANDS);
          setRoutingKey(Key.FILES);
          setQueueName(Queue.COMMANDS_FILES);
        }
      },
      new QueueParams() {
        {
          setExchangeName(WIDGET_CONFIGS_EXCHANGE_NAME);
          setRoutingKey(REEL_CONFIG_ROUTING_KEY);
          setQueueName(REEL_CONFIG_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(WIDGET_CONFIGS_EXCHANGE_NAME);
          setRoutingKey(GOALS_CONFIG_ROUTING_KEY);
          setQueueName(GOALS_CONFIG_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(PAYMENTS_EXCHANGE_NAME);
          setRoutingKey(PAYMENT_REEL_ROUTING_KEY);
          setQueueName(PAYMENT_REEL_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(PAYMENTS_EXCHANGE_NAME);
          setRoutingKey(PAYMENT_GOAL_ROUTING_KEY);
          setQueueName(PAYMENT_GOAL_QUEUE_NAME);
        }
      },
      new QueueParams() {
        {
          setExchangeName(PAYMENTS_EXCHANGE_NAME);
          setRoutingKey(PAYMENT_CONTRIBUTIONS_ROUTING_KEY);
          setQueueName(PAYMENT_CONTRIBUTIONS_QUEUE_NAME);
        }
      },
    }
  );

  @Override
  public void initialize(Channel channel, String name) throws IOException {
    channel.exchangeDeclare(COMMANDS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    channel.exchangeDeclare(
      WIDGET_CONFIGS_EXCHANGE_NAME,
      BuiltinExchangeType.TOPIC
    );
    channel.exchangeDeclare(PAYMENTS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
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
