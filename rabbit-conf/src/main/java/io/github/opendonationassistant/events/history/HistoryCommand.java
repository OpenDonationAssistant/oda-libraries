package io.github.opendonationassistant.events.history;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.data.annotation.MappedProperty;
import io.micronaut.data.model.DataType;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

public class HistoryCommand {

  @Serdeable
  public static record AddDonation(
    String id,
    String paymentId,
    String nickname,
    String cleanNickname,
    String recipientId,
    Amount amount,
    String message,
    String cleanMessage,
    String system,
    @Nullable String externalId,
    Instant authorizationTimestamp,
    @MappedProperty(type = DataType.JSON) List<ActionRequest> actions
  ) {}

  @Serdeable
  public static record RunActions(
    boolean triggerAlert,
    boolean triggerReel,
    boolean triggerDonaton,
    boolean addToTop,
    boolean addToGoal
  ) {}

  @Serdeable
  public static record LinkAttachment(
    String recipientId,
    String historyItemId,
    String id,
    String url,
    String title,
    String thumbnail
  ) {}

  @Serdeable
  public static record LinkReelResult(
    String recipientId,
    String historyItemId,
    String widgetId,
    String optionId,
    String title
  ) {}

  @Serdeable
  public static record LinkAlertMedia(
    String recipientId,
    String historyItemId,
    String url
  ) {}

  @Serdeable
  public static record LinkGoal(
    String recipientId,
    String historyItemId,
    String widgetId,
    String goalId,
    String title
  ) {}

  @Serdeable
  public static record ActionRequest(
    String id,
    String actionId,
    String name,
    Integer amount,
    Map<String, Object> payload
  ) {}
}
