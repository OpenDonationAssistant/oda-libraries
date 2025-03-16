package io.github.opendonationassistant.events.goal;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient("amq.topic")
public interface GoalCommandSender {
  void send(@Binding String binding, GoalCommand command);

  default void sendGoalCommand(String recipientId, GoalCommand command) {
    send("%sgoal".formatted(recipientId), command);
  }
}
