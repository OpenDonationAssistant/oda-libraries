package io.github.opendonationassistant.commons;

public enum Systems {
  ODA("ODA"),
  DonationAlerts("DonationAlerts"),
  DonateX("DonateX"),
  DonatePayRu("DonatePay"),
  Patreon("Patreon"),
  StreamElements("StreamElements"),
  Streamlabs("Streamlabs"),
  Tribute("Tribute"),
  Twitch("Twitch"),
  VKLive("VKLive"),
  Kick("Kick"),
  Youtube("YouTube");

  private String name;

  Systems(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
