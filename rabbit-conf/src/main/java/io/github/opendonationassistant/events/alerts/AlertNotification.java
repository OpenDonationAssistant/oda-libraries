package io.github.opendonationassistant.events.alerts;

import java.time.Instant;
import java.util.ArrayList;

import io.github.opendonationassistant.commons.Amount;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class AlertNotification {

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
  private AlertMedia media;

  @Serdeable
  public static record AlertMedia(String url) {}

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
    this.attachments = attachments;
  }


  public String getGoal() {
    return goal;
  }


  public void setGoal(String goal) {
    this.goal = goal;
  }


  public Instant getAuthorizationTimestamp() {
    return authorizationTimestamp;
  }


  public void setAuthorizationTimestamp(Instant authorizationTimestamp) {
    this.authorizationTimestamp = authorizationTimestamp;
  }


  public AlertMedia getMedia() {
    return media;
  }


  public void setMedia(AlertMedia media) {
    this.media = media;
  }

}
