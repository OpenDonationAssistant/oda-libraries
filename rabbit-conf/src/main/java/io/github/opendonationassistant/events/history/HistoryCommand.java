package io.github.opendonationassistant.events.history;

import io.micronaut.serde.annotation.Serdeable;

public class HistoryCommand {

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

}
