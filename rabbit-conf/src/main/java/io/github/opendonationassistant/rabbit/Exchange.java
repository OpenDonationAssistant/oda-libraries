package io.github.opendonationassistant.rabbit;

public class Exchange {

  // просто потому что системный
  public static final String AMQ_TOPIC = "amq.topic";

  // которые пригодятся надолго,
  // Exchange = сущность,
  // binding - этап/фаза,
  // messageheader = тип команды/эвента
  public static final String PAYMENTS = "payments";
  public static final String GOALS = "goals";
  public static final String ACTIONS = "actions";
  public static final String WIDGETS = "widgets";
  public static final String VOTING = "voting";
  public static final String TWITCH = "twitch";
  public static final String REEL = "reel";

  public static class Configs {

    public static final String WIDGETS = "changes.widgets";
  }

  public static final String EVENTS = "events";
  public static final String COMMANDS = "commands";
}
