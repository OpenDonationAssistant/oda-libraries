package io.github.opendonationassistant.events.voting;

import static io.github.opendonationassistant.commons.ToString.asBytes;

import io.github.opendonationassistant.commons.logging.ODALogger;
import io.github.opendonationassistant.rabbit.Exchange;
import io.github.opendonationassistant.rabbit.Key;
import io.micronaut.messaging.annotation.MessageHeader;
import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class VotingFacage {

  private final ODALogger log = new ODALogger(VotingMessagingClient.class);
  private final VotingMessagingClient client;

  @Inject
  public VotingFacage(VotingMessagingClient client) {
    this.client = client;
  }

  public CompletableFuture<Void> sendState(VotingState state) {
    log.debug("Send Voting state", Map.of("state", state));
    return client.send(Key.ALL, VotingState.MESSAGE_TYPE, state);
  }

  @RabbitClient(Exchange.VOTING)
  public static interface VotingMessagingClient {
    CompletableFuture<Void> sendMessage(
      @Binding String binding,
      @MessageHeader String type,
      byte[] command
    );

    default CompletableFuture<Void> send(
      @Binding String binding,
      @MessageHeader String type,
      Object payload
    ) {
      return sendMessage(binding, type, asBytes(payload));
    }
  }
}
