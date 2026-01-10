package io.github.opendonationassistant.rabbit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// prettier-ignore ON
public class Mapping {

  public static final List<QueueParams> PAYMENTS =
    Arrays.asList(new QueueParams[] {
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
        setRoutingKey(Key.PAYMENTS); // TODO Delete
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
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.DONATON);
        setQueueName(Queue.Payments.DONATON);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.DONATIONGOAL);
        setQueueName(Queue.Payments.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.ACTIONS);
        setQueueName(Queue.Payments.ACTIONS);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.PAYMENTS);
        setRoutingKey(Key.CONTRIBUTIONS); // TODO Delete
        setQueueName(Queue.Payments.CONTRIBUTIONS);
      }},
    });

  public static final List<QueueParams> OLD =
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
    });

  // TODO используются?
  public static final List<QueueParams> COMMANDS =
    Arrays.asList(new QueueParams[] {
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
    });

  public static final List<QueueParams> ACTIONS =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.ACTIONS);
        setRoutingKey(Key.FINALIZED);
        setQueueName(Queue.History.ACTIONS);
      }}
    });

  public static final List<QueueParams> WIDGETS =
    Arrays.asList(new QueueParams[] {
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
        setRoutingKey(Key.DONATIONGOAL); // TODO почему поменялось?
        setQueueName(Queue.Configs.GOAL);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.Configs.WIDGETS);
        setRoutingKey(Key.MEDIA);
        setQueueName(Queue.Configs.MEDIA);
      }},
    });

  public static final List<QueueParams> GOALS =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.GOALS);
        setRoutingKey(Key.COMMAND);
        setQueueName(Queue.Goal.COMMAND);
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

  public static final List<QueueParams> TWITCH =
    Arrays.asList(new QueueParams[] {
      new QueueParams() {{
        setExchangeName(Exchange.TWITCH);
        setRoutingKey(Key.COMMAND);
        setQueueName(Queue.Twitch.COMMAND);
      }},
      new QueueParams() {{
        setExchangeName(Exchange.TWITCH);
        setRoutingKey("event.*");
        setQueueName(Queue.Twitch.COMMAND);
      }}
    });

  public static List<QueueParams> getQueues() {
    var result = new ArrayList<QueueParams>();
    result.addAll(PAYMENTS);
    result.addAll(OLD);
    result.addAll(COMMANDS);
    result.addAll(WIDGETS);
    result.addAll(GOALS);
    result.addAll(ACTIONS);
    result.addAll(VOTING);
    result.addAll(TWITCH);
    return result;
  }

}
