package io.github.opendonationassistant.rabbit;

import java.util.Arrays;
import java.util.List;

public class Mapping {

  public static final List<QueueParams> QUEUES = Arrays.asList(
    // prettier-ignore ON
    new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.REEL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.DONATON);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.CONTRIBUTIONS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.HISTORY);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.HISTORY);
        setQueueName(Queue.Commands.HISTORY);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.REEL);
        setQueueName(Queue.Commands.REEL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.CONFIG);
        setQueueName(Queue.Commands.CONFIG);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.FILES);
        setQueueName(Queue.Commands.FILES);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.ALERTS);
        setQueueName(Queue.Commands.ALERTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.NOTIFICATIONS);
        setQueueName(Queue.Commands.NOTIFICATIONS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.COMMANDS);
        setRoutingKey(Key.WIDGETS);
        setQueueName(Queue.Commands.WIDGETS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.REEL);
        setQueueName(Queue.Configs.REEL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.DONATON);
        setQueueName(Queue.Configs.DONATON);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.GOAL);
        setQueueName(Queue.Configs.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.REEL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.CONTRIBUTIONS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.DONATON);
      }},
    }
    // prettier-ignore OFF
  );
}
