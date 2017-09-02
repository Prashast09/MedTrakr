package medtrakr.cricbuzz.ethens.medtrakr.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Date;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.common.receiver.Alarm;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;

/**
 * Created by ethens on 02/09/17.
 */

public class AlarmUtils {

  @Inject Context context;

  @Inject AlarmUtils() {

  }

  /**
   * Set alarms for the user based on the reminders fetched from the server
   */
  public void setAlarms(ReminderConfig newReminderConfig, boolean update, String intentId,
      int oldIntentId, String oldNotificationMessage) {

    if (newReminderConfig == null || !(String.valueOf(newReminderConfig.getReminderTime()) == null
        && String.valueOf(newReminderConfig.getReminderTime()) == (null))) {
      if (update) cancelAlarm(newReminderConfig, oldIntentId, oldNotificationMessage);
      setAlarmIntent(newReminderConfig, context, Integer.parseInt(intentId));
    }
  }

  public void cancelAlarm(ReminderConfig newReminderConfig, int oldIntentId,
      String oldNotificationMessage) {
    int intentId;
    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    if (newReminderConfig.getIntentId() != null) {
      Intent notificationMessage = new Intent(context, Alarm.class);
      notificationMessage.putExtra("message", oldNotificationMessage);
      if (oldIntentId == 0) {
        intentId = Integer.parseInt(newReminderConfig.getIntentId());
      } else {
        intentId = oldIntentId;
      }
      PendingIntent pi = PendingIntent.getBroadcast(context, intentId, notificationMessage,
          PendingIntent.FLAG_CANCEL_CURRENT);
      am.cancel(pi);
      pi.cancel();
      if (newReminderConfig.getRecurringType() != 0) {
        PendingIntent pi1 = PendingIntent.getBroadcast(context, intentId + 1, notificationMessage,
            PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pi2 = PendingIntent.getBroadcast(context, intentId + 2, notificationMessage,
            PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(pi1);
        pi1.cancel();
        am.cancel(pi2);
        pi2.cancel();
      }
    }
  }

  /**
   * Prepare the alarm intent to be sent to the broadcast receiver
   */
  private void setAlarmIntent(ReminderConfig reminderConfig, Context context, int intentId) {

    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    long reminderDate = reminderConfig.getReminderTime().getTime() - (new Date().getTime());

    if (reminderDate > 0) {
      Intent notificationMessage = new Intent(context, Alarm.class);
      notificationMessage.putExtra("message", reminderConfig.getReminderText());

      PendingIntent pi = PendingIntent.getBroadcast(context, intentId, notificationMessage,
          PendingIntent.FLAG_UPDATE_CURRENT);

      am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + reminderDate, pi);
    }
  }
}

