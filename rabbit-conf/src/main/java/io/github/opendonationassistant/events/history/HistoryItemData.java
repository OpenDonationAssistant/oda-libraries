package io.github.opendonationassistant.events.history;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.model.DataType;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Serdeable
public record HistoryItemData(
  @Id String id,
  String paymentId,
  String nickname,
  String cleanNickname,
  String recipientId,
  Amount amount,
  String message,
  String cleanMessage,
  String system,
  String externalId,
  Instant authorizationTimestamp,
  @MappedProperty(type = DataType.JSON) List<Attachment> attachments,
  @MappedProperty(type = DataType.JSON) List<TargetGoal> goals,
  @MappedProperty(type = DataType.JSON) List<ReelResult> reelResults,
  @MappedProperty(type = DataType.JSON) List<ActionRequest> actions
) {
  @Serdeable
  public record ActionRequest(String id, String actionId, Map<String, Object> payload){}
}
