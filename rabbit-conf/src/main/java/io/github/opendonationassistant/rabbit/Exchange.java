package io.github.opendonationassistant.rabbit;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Exchange {

  // просто потому что системный
  public static final String AMQ_TOPIC = "amq.topic";

  // которые пригодятся надолго,
  // Exchange = сущность,
  // binding - этап/фаза,
  // messageheader = тип команды/эвента
  public static final String PAYMENTS = "payments";
  public static final String GOALS = "goals";
  public static final String ACTIONS = "actions";
  public static final String WIDGETS = "widgets";
  public static final String VOTING = "voting";
  public static final String TWITCH = "twitch";
  public static final String HISTORY = "history";
  public static final String MEDIA = "media";
  public static final String REEL = "reel";
  public static final String FILES = "files";
  public static final String DONATON = "donaton";

  public static class Configs {

    public static final String WIDGETS = "changes.widgets";
  }

  private final String name;
  private final Map<String, Queue> bindings;

  public Exchange(String name) {
    this(name, new HashMap<>());
  }

  private Exchange(String name, Map<String, Queue> bindings) {
    this.name = name;
    this.bindings = bindings;
  }

  public static Exchange Exchange(String name, Map<String, Queue> bindings) {
    return new Exchange(name, bindings);
  }

  public static Exchange Exchange(
    Channel channel,
    String name,
    Map<String, Queue> bindings
  ) {
    Exchange exchange = new Exchange(name, bindings);
    exchange.connect(channel);
    return exchange;
  }

  public Exchange bind(String routingKey, Queue queue) {
    bindings.put(routingKey, queue);
    return this;
  }

  public void connect(Channel channel) {
    try {
      channel.exchangeDeclare(name, BuiltinExchangeType.TOPIC);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    bindings
      .entrySet()
      .forEach(entry ->
        declareAndBind(channel, entry.getKey(), entry.getValue().name())
      );
  }

  private void declareAndBind(
    Channel channel,
    String routingKey,
    String queueName
  ) {
    try {
      channel.queueDeclare(queueName, true, false, false, new HashMap<>());
      channel.queueBind(queueName, name, routingKey);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
