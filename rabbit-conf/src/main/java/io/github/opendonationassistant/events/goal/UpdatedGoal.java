package io.github.opendonationassistant.events.goal;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class UpdatedGoal {
  private String goalId;
  private String widgetId;
  private String recipientId;
  private String fullDescription;
  private String briefDescription;
  private Amount requiredAmount;
  private Amount accumulatedAmount;
  private Boolean isDefault;

  public String getGoalId() {
    return goalId;
  }
  public void setGoalId(String goalId) {
    this.goalId = goalId;
  }
  public String getFullDescription() {
    return fullDescription;
  }
  public void setFullDescription(String fullDescription) {
    this.fullDescription = fullDescription;
  }
  public String getBriefDescription() {
    return briefDescription;
  }
  public void setBriefDescription(String briefDescription) {
    this.briefDescription = briefDescription;
  }
  public Amount getRequiredAmount() {
    return requiredAmount;
  }
  public void setRequiredAmount(Amount requiredAmount) {
    this.requiredAmount = requiredAmount;
  }
  public Amount getAccumulatedAmount() {
    return accumulatedAmount;
  }
  public void setAccumulatedAmount(Amount accumulatedAmount) {
    this.accumulatedAmount = accumulatedAmount;
  }
  public String getRecipientId() {
    return recipientId;
  }
  public void setRecipientId(String recipientId) {
    this.recipientId = recipientId;
  }
  public Boolean getIsDefault() {
    return isDefault;
  }
  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }
  public String getWidgetId() {
    return widgetId;
  }
  public void setWidgetId(String widgetId) {
    this.widgetId = widgetId;
  }

}
