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

  @Inject
  public GoalFacade(GoalCommandSender sender) {
    this.sender = sender;
  }

  private void run(Object command) throws IOException {
    var commandName = command.getClass().getSimpleName();
    log.debug(commandName, Map.of("command", command));
    sender.sendCommand(
      commandName,
      ObjectMapper.getDefault().writeValueAsBytes(command)
    );
  }

  public void run(CountPaymentInSpecifiedGoalCommand command)
    throws IOException {
    this.run(command);
  }

  public void run(CountPaymentInDefaultGoalCommand command) throws IOException {
    this.run(command);
  }

  @RabbitClient(Exchange.GOALS)
  public static interface GoalCommandSender {
    @Binding(Key.COMMAND)
    void sendCommand(@MessageHeader String type, byte[] command);
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
