package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.ObjectMapper;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.Map;

@Singleton
public class GoalFacade {

  private final ODALogger log = new ODALogger(this);

  private final GoalCommandSender sender;
  private final ObjectMapper mapper;

  @Inject
  public GoalFacade(GoalCommandSender sender, ObjectMapper mapper) {
    this.sender = sender;
    this.mapper = mapper;
  }

  public void run(CountPaymentInSpecifiedGoalCommand command) {
    this.runCommand(command);
  }

  public void run(CountPaymentInDefaultGoalCommand command) {
    this.runCommand(command);
  }

  @RabbitClient(Exchange.GOALS)
  public static interface GoalCommandSender {
    @Binding(Key.COMMAND)
    void sendCommand(@MessageHeader String type, byte[] command);
  }

  private void runCommand(Object command) {
    var commandName = command.getClass().getSimpleName();
    log.debug(commandName, Map.of("command", command));
    try {
      sender.sendCommand(commandName, mapper.writeValueAsBytes(command));
    } catch (IOException e) {
      log.error("Error while sending command", Map.of("error", e.getMessage()));
      throw new RuntimeException(e);
    }
  }

  @Serdeable
  public static record CountPaymentInDefaultGoalCommand(
    String paymentId,
    String recipientId,
    Amount amount
  ) {}

  @Serdeable
  public static record CountPaymentInSpecifiedGoalCommand(
    String paymentId,
    String recipientId,
    String goalId,
    Amount amount
  ) {}
}
