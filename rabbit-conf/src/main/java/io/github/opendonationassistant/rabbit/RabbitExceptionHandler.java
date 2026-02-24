package io.github.opendonationassistant.rabbit;

import java.util.UUID;

import io.github.opendonationassistant.commons.AbstractExceptionHandler;
import io.micronaut.rabbitmq.exception.RabbitListenerException;
import io.micronaut.rabbitmq.exception.RabbitListenerExceptionHandler;

public class RabbitExceptionHandler
  extends AbstractExceptionHandler
  implements RabbitListenerExceptionHandler {

  @Override
  public void handle(RabbitListenerException exception) {
    var id = UUID.randomUUID().toString();
    log(exception, id);
  }
}
