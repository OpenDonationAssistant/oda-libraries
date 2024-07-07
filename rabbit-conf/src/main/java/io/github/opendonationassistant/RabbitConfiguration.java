package io.github.opendonationassistant;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.HashMap;

@Singleton
public class RabbitConfiguration extends ChannelInitializer {

  public static final String AMQ_TOPIC = "amq.topic";
  public static final String COMMANDS_EXCHANGE_NAME = "commands";
  public static final String COMMANDS_QUEUE_NAME = "commands.history";
  public static final String PAYMENT_CONTRIBUTIONS_QUEUE_NAME =
    "payments_for_contributions";
  public static final String HISTORY_COMMANDS_ROUTING_KEY = "history";
  public static final String PAYMENTS_ROUTING_KEY = "payments";

  @Override
  public void initialize(Channel channel, String name) throws IOException {
    channel.exchangeDeclare(COMMANDS_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
    declareAndBind(
      channel,
      new QueueParams() {
        {
          setQueueName(PAYMENT_CONTRIBUTIONS_QUEUE_NAME);
          setExchangeName(AMQ_TOPIC);
          setRoutingKey(PAYMENTS_ROUTING_KEY);
        }
      }
    );
    declareAndBind(
      channel,
      new QueueParams() {
        {
          setQueueName("testname");
          setExchangeName(AMQ_TOPIC);
          setRoutingKey("testkey");
        }
      }
    );
    declareAndBind(
      channel,
      new QueueParams() {
        {
          setQueueName(COMMANDS_QUEUE_NAME);
          setExchangeName(COMMANDS_EXCHANGE_NAME);
          setRoutingKey(HISTORY_COMMANDS_ROUTING_KEY);
        }
      }
    );
  }

  private void declareAndBind(Channel channel, QueueParams params)
    throws IOException {
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
