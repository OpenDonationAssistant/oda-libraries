package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;

@RabbitClient(Exchange.EVENTS)
public interface GoalSender {
  void send(@Binding String binding, UpdatedGoal goal);

  default void sendUpdatedGoal(UpdatedGoal goal) {
    send(Key.GOAL, goal);
  }
}
