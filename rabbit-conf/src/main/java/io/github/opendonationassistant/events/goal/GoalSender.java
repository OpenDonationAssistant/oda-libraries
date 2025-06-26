package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.GOALS)
public interface GoalSender {
  final ODALogger log = new ODALogger(GoalSender.class);

  void send(@Binding String binding, UpdatedGoal goal);

  default void sendGoal(Stage stage, UpdatedGoal goal) {
    log.info("Send UpdatedGoal", Map.of("goal", goal, "stage", stage));
    send(stage.binding(), goal);
  }

  public static enum Stage {
    AFTER_PAYMENT("afterpayment"),
    AFTER_AUTOMATION("afterautomation"),
    SYNCED("synced"),
    FINALIZED("finalized");

    private String binding;

    Stage(String binding) {
      this.binding = binding;
    }

    public String binding() {
      return this.binding;
    }
  }
}
