package io.github.opendonationassistant.events.voting;

import io.micronaut.serde.annotation.Serdeable;
import java.util.List;

@Serdeable
public record VotingState(
  String id,
  String name,
  List<VoteOptionState> options
) {
  @Serdeable
  public static record VoteOptionState(
    String id,
    String name,
    Integer votes,
    Boolean isDefault,
    Boolean userCreated
  ) {}
}
