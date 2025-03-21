package io.github.opendonationassistant.events;

import io.github.opendonationassistant.commons.Amount;
import io.github.opendonationassistant.events.alerts.AlertNotification;
import io.micronaut.serde.annotation.Serdeable;
import java.time.Instant;
import java.util.ArrayList;

@Serdeable
public class CompletedPaymentNotification {

  private String id;
  private String nickname;
  private String message;
  private String recipientId;
  private Amount amount;
  private String confirmation;
  private Boolean failed;
  private java.util.List<String> attachments = new ArrayList<>();
  private String goal;
  private Instant authorizationTimestamp;

  public AlertNotification toAlertNotification() {
    AlertNotification notification = new AlertNotification();
    notification.setId(id);
    notification.setNickname(nickname);
    notification.setMessage(message);
    notification.setRecipientId(recipientId);
    notification.setAmount(amount);
    notification.setConfirmation(confirmation);
    notification.setFailed(failed);
    notification.setAttachments(attachments);
    notification.setGoal(goal);
    notification.setAuthorizationTimestamp(authorizationTimestamp);
    return notification;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getRecipientId() {
    return recipientId;
  }

  public void setRecipientId(String recipientId) {
    this.recipientId = recipientId;
  }

  public Amount getAmount() {
    return amount;
  }

  public void setAmount(Amount amount) {
    this.amount = amount;
  }

  public String getConfirmation() {
    return confirmation;
  }

  public void setConfirmation(String confirmation) {
    this.confirmation = confirmation;
  }

  public Boolean getFailed() {
    return failed;
  }

  public void setFailed(Boolean failed) {
    this.failed = failed;
  }

  public java.util.List<String> getAttachments() {
    return attachments;
  }

  public void setAttachments(java.util.List<String> attachments) {
    this.attachments = attachments == null ? new ArrayList<>() : attachments;
  }

  public Instant getAuthorizationTimestamp() {
    return authorizationTimestamp;
  }

  public void setAuthorizationTimestamp(Instant authorizationTimestamp) {
    this.authorizationTimestamp = authorizationTimestamp;
  }

  public String getGoal() {
    return goal;
  }

  public void setGoal(String goal) {
    this.goal = goal;
  }

  @Override
  public String toString() {
    return (
      "{\"_type\"=\"CompletedPaymentNotification\",\"id\"=\"" +
      id +
      "\", nickname\"=\"" +
      nickname +
      "\", message\"=\"" +
      message +
      "\", recipientId\"=\"" +
      recipientId +
      "\", amount\"=\"" +
      amount +
      "\", confirmation\"=\"" +
      confirmation +
      "\", failed\"=\"" +
      failed +
      "\", attachments\"=\"" +
      attachments +
      "\", goal\"=\"" +
      goal +
      "\", authorizationTimestamp\"=\"" +
      authorizationTimestamp +
      "}"
    );
  }
}
