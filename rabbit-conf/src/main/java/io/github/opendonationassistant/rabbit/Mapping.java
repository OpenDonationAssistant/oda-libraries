package io.github.opendonationassistant.rabbit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// prettier-ignore ON
public class Mapping {

  public static final List<QueueParams> WIDGETS =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.WIDGETS);
        setRoutingKey(Key.COMMAND);
        setQueueName(Queue.Commands.WIDGETS);
      }},
    });

  public static final List<QueueParams> FILES =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.FILES);
        setRoutingKey(Key.COMMAND);
        setQueueName(Queue.Commands.FILES);
      }},
    });

  public static final List<QueueParams> GOALS =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey("event.PaymentEvent");
        setQueueName(Queue.Goal.EVENTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.HISTORY);
        setRoutingKey("event.HistoryItemEvent");
        setQueueName(Queue.Goal.EVENTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.GOALS);
        setRoutingKey(Key.COMMAND);
        setQueueName(Queue.Goal.COMMAND);
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
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.GOAL);
        setQueueName(Queue.Configs.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.DONATIONGOAL); // TODO почему поменялось?
        setQueueName(Queue.Configs.GOAL);
      }}
    });

  public static final List<QueueParams> VOTING =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.VOTING);
        setRoutingKey(Key.COMMAND);
        setQueueName(Queue.Voting.COMMAND);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.VOTING);
        setRoutingKey(Key.ALL);
        setQueueName(Queue.Configs.EVENTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.PAYMENTS);
        setQueueName(Queue.Voting.EVENTS);
      }}
    });

  public static final List<QueueParams> MEDIA =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.HISTORY);
        setRoutingKey("event.HistoryItemEvent");
        setQueueName(Queue.Media.EVENTS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.MEDIA);
        setQueueName(Queue.Configs.MEDIA);
      }},
    });

  public static List<QueueParams> getQueues() {
    var result = new ArrayList<QueueParams>();
    result.addAll(WIDGETS);
    result.addAll(GOALS);
    result.addAll(VOTING);
    result.addAll(MEDIA);
    return result;
  }

}
