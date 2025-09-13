package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient("amq.topic")
public interface GoalWidgetCommandSender {
  final ODALogger log = new ODALogger(GoalWidgetCommandSender.class);

  void send(@Binding String binding, GoalWidgetCommand command);

  default void sendGoalCommand(String recipientId, GoalWidgetCommand command) {
    log.info(
      "Send GoalCommand",
      Map.of("command", command, "recipientId", recipientId)
    );
    send("%sgoal".formatted(recipientId), command);
  }
}
