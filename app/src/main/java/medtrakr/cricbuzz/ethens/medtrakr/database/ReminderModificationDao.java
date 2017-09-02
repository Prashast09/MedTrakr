package medtrakr.cricbuzz.ethens.medtrakr.database;

import android.content.ContentValues;
import de.greenrobot.event.EventBus;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.AppCommonEvent;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.ReminderModificationEvent;
import medtrakr.cricbuzz.ethens.medtrakr.utils.AlarmUtils;
import medtrakr.cricbuzz.ethens.medtrakr.utils.StringUtils;

/**
 * Created by ethens on 02/09/17.
 */

public class ReminderModificationDao extends BaseDao {
  @Inject RemindersOrm remindersOrm;
  @Inject AlarmUtils alarmUtils;

  @Inject ReminderModificationDao() {
  }

  public long addReminder(ReminderConfig reminderConfig, boolean update) {
    long reminderId;
    int intentId = (int) System.currentTimeMillis();
    if (intentId < 0) intentId *= -1;
    int oldIntentId = 0;
    String oldNotificationMessage = null;
    if (update) {
      oldNotificationMessage = reminderConfig.getReminderText();
      if (!StringUtils.isBlank(reminderConfig.getIntentId())) {
        oldIntentId = Integer.parseInt(reminderConfig.getIntentId());
      }
      reminderConfig.setIntentId(String.valueOf(intentId));
      ContentValues cv = remindersOrm.addReminder(reminderConfig, true);
      String strFilter = "_id=" + reminderConfig.getReminderId();
      reminderId =
          writeableDatabase.update(TrakrContract.Reminders.TABLE_NAME, cv, strFilter, null);
    } else {
      ContentValues cv = remindersOrm.addReminder(reminderConfig, false);
      reminderId = writeableDatabase.insert(TrakrContract.Reminders.TABLE_NAME, null, cv);
      EventBus.getDefault().post(new ReminderModificationEvent.NewReminderAddedEvent());
    }
    alarmUtils.setAlarms(reminderConfig, update,reminderConfig.getIntentId(), oldIntentId,
        oldNotificationMessage);
    EventBus.getDefault().post(new AppCommonEvent.ReminderModifiedEvent());
    return reminderId;
  }

  /**
   * @param reminderId Reminder Id
   */
  public void deleteReminder(String reminderId, ReminderConfig reminderConfig) {
    ContentValues cv = remindersOrm.deleteReminder();
    String selection = TrakrContract.Reminders._ID + "=" + reminderId;
    writeableDatabase.update(TrakrContract.Reminders.TABLE_NAME, cv, selection, null);
    if (StringUtils.isBlank(reminderConfig.getIntentId())) {
      return;
    }
    alarmUtils.cancelAlarm(reminderConfig, Integer.parseInt(reminderConfig.getIntentId()),
        reminderConfig.getReminderText());
  }
}