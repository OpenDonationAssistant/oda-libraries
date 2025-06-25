package io.github.opendonationassistant.events.widget;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import java.util.Map;

@RabbitClient(Exchange.Configs.WIDGETS)
public interface WidgetChangedNotificationSender {
  static ODALogger log = new ODALogger(WidgetChangedNotificationSender.class);

  void _send(@Binding String binding, WidgetChangedEvent event);

  default void send(String binding, WidgetChangedEvent event) {
    log.info(
      "Send WidgetChangedEvent",
      Map.of("event", event, "binding", binding)
    );
    this._send(binding, event);
  }
}
