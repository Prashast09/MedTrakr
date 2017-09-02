package medtrakr.cricbuzz.ethens.medtrakr.eventbus;

import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;

/**
 * Created by ethens on 01/09/17.
 */

public class CalendarNavigationEvents {

  public static class ReminderCardClickEvent {

    ReminderConfig reminderConfig;

    public ReminderConfig getReminderConfig() {
      return reminderConfig;
    }

    public ReminderCardClickEvent(ReminderConfig reminderConfig) {

      this.reminderConfig = reminderConfig;
    }
  }

  public static class DialogItemSelectionEvent{
    private String text;
    private Integer data;

    public String getText() {
      return text;
    }

    public Integer getData() {
      return data;
    }

    public DialogItemSelectionEvent(String text, Integer data) {
      this.text = text;
      this.data = data;

    }
  }
}
