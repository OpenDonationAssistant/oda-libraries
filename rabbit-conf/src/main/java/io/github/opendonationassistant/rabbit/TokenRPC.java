package io.github.opendonationassistant.rabbit;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;
import io.micronaut.serde.annotation.Serdeable;

@RabbitClient("rpc")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
public interface TokenRPC {
  @Binding("token")
  String token(TokenRequest request);

  @Serdeable
  public static record TokenRequest(
    String recipientId,
    String refreshTokenId
  ) {}
}
