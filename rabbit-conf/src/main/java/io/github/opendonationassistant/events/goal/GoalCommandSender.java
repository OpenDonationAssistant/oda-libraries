package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient("amq.topic")
public interface GoalCommandSender {
  final ODALogger log = new ODALogger(GoalCommandSender.class);

  void send(@Binding String binding, GoalCommand command);

  default void sendGoalCommand(String recipientId, GoalCommand command) {
    log.info(
      "Send GoalCommand",
      Map.of("command", command, "recipientId", recipientId)
    );
    send("%sgoal".formatted(recipientId), command);
  }
}
