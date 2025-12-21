package io.github.opendonationassistant.events.voting;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record VotingState(
  String id,
  String recipientId,
  String name,
  Conversion conversion,
  List<VoteOptionState> options
) {

  public static final String MESSAGE_TYPE = "VotingState";

  @Serdeable
  public static record VoteOptionState(
    String id,
    String name,
    Integer votes,
    Boolean isDefault,
    Boolean userCreated
  ) {}

  @Serdeable
  public static record Conversion(
    Integer originAmount,
    Integer convertedAmount
  ) {}
}
