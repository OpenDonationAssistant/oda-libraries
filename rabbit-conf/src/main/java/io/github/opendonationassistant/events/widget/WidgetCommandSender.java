package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.events.widget.Widget.WidgetConfig;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.serde.annotation.Serdeable;
import java.util.Map;

@RabbitClient(Exchange.WIDGETS)
public interface WidgetCommandSender {
  final ODALogger log = new ODALogger(WidgetCommandSender.class);

  @Serdeable
  public record WidgetUpdateCommand(String id, WidgetConfig patch) {}

  default void send(WidgetUpdateCommand command) {
    log.info("Send WidgetUpdateCommand", Map.of("command", command));
    send(Key.COMMAND, command);
  }

  void send(@Binding String binding, WidgetUpdateCommand command);
}
