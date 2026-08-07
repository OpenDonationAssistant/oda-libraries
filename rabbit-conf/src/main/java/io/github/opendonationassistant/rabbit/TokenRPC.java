package io.github.opendonationassistant.rabbit;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import io.micronaut.rabbitmq.annotation.RabbitProperty;
import io.micronaut.serde.annotation.Serdeable;
import java.util.concurrent.CompletableFuture;
import org.jspecify.annotations.Nullable;

@RabbitClient("rpc")
@RabbitProperty(name = "replyTo", value = "amq.rabbitmq.reply-to")
public interface TokenRPC {
  @Binding("token")
  CompletableFuture<TokenResponse> token(TokenRequest request);

  @Serdeable
  public static record TokenRequest(
    String recipientId,
    String refreshTokenId
  ) {}

  @Serdeable
  public static record TokenResponse(@Nullable String token, String message) {}
}
