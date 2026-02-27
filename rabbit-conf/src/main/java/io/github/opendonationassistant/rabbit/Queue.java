package io.github.opendonationassistant.rabbit;

public class Queue {

  public static class Commands {

    public static final String HISTORY = "commands.history";
    public static final String CONFIG = "commands.config";
    public static final String FILES = "commands.files";
    public static final String ALERTS = "commands.alerts";
    public static final String NOTIFICATIONS = "commands.notifications";
    public static final String REEL = "commands.reel";
    public static final String WIDGETS = "commands.widgets";
  }

  public static class Widgets {

    public static final String COMMAND = "widgets.command";
  }

  public static class Payments {

    public static final String MEDIA = "payments";
    public static final String AUTOMATION = "payments.automation";
    public static final String ACTIONS = "payments.actions";
    public static final String ALERTS = "payments.alerts";
    public static final String CONTRIBUTIONS = "payments_for_contributions";
    public static final String REEL = "payments_for_reel";
    public static final String GOAL = "payments_for_goal";
    public static final String HISTORY = "payments_for_history";
    public static final String DONATON = "payments_for_donaton";
  }

  public static class Automation {

    public static final String EVENTS = "automation.events";
    public static final String GOAL = "automation.goals";
  }

  public static class Goal {

    public static final String COMMAND = "goal.command";
    public static final String CALCULATED = "goal.calculated";
    public static final String SYNCED = "goal.calculated";
    public static final String FINISHED = "goal.finished";
    public static final String EVENTS = "goal.events";
  }

  public static class History {

    public static final String GOAL = "history.goal";
    public static final String ACTIONS = "history.actions";
    public static final String EVENTS = "history.events";
  }

  public static class Configs {

    public static final String GOAL = "config.goals";
    public static final String REEL = "config.reel";
    public static final String DONATON = "config.donaton";
    public static final String MEDIA = "config.media";
    public static final String EVENTS = "config.events";
  }

  public static class Voting {

    public static final String COMMAND = "voting.command";
    public static final String EVENTS = "voting.events";
  }

  public static class Twitch {

    public static final String COMMAND = "twitch.command";
    public static final String EVENTS = "twitch.events";
  }

  public static class Media {

    public static final String COMMAND = "media.command";
    public static final String EVENTS = "media.events";
  }
}
