package io.github.opendonationassistant.rabbit;

import java.util.Arrays;
import java.util.List;

// prettier-ignore ON
public class Mapping {

  public static final List<QueueParams> QUEUES =
    Arrays.asList(new QueueParams[] {
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
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.MEDIA);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.ALERTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.AMQ_TOPIC);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.AUTOMATION);
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
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.MEDIA);
        setQueueName(Queue.Configs.MEDIA);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.REEL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.DONATON);
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
        setQueueName(Queue.Payments.HISTORY);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.MEDIA);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.ALERTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Payments.AUTOMATION);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.GOALS);
        setRoutingKey(Key.AFTERPAYMENT);
        setQueueName(Queue.Automation.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.GOALS);
        setRoutingKey(Key.AFTERAUTOMATION);
        setQueueName(Queue.Goal.CALCULATED);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.GOALS);
        setRoutingKey(Key.SYNCED);
        setQueueName(Queue.Goal.FINISHED);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.GOALS);
        setRoutingKey(Key.FINALIZED);
        setQueueName(Queue.History.GOAL);
      }},
    });
}
