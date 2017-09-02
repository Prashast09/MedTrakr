package medtrakr.cricbuzz.ethens.medtrakr.database;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.database.query.ReminderInfoQuery;

/**
 * Created by ethens on 02/09/17.
 */

public class ReminderInfoDao extends BaseDao {

  @Inject ReminderInfoQuery remindersQuery;
  @Inject RemindersOrm remindersOrm;

  @Inject ReminderInfoDao() {
  }

  public ReminderConfig getReminderWithId(long reminderId) {
    return getFirstRecord(remindersQuery.getReminderForId(reminderId),
        this::getCommonReminderConfig);
  }

  public List<ReminderConfig> getAllReminders() {
    List<ReminderConfig> reminderConfigList = new ArrayList<>();
    String query = remindersQuery.getAllReminders();
    runRawQuery(query, cursor -> reminderConfigList.add(remindersOrm.getReminderConfig(cursor)));
    return reminderConfigList;
  }

  public List<ReminderConfig> getMedTakenConfig() {
    List<ReminderConfig> reminderConfigList = new ArrayList<>();
    String query = remindersQuery.getMedTakenQuery();
    runRawQuery(query, cursor -> reminderConfigList.add(remindersOrm.getReminderConfig(cursor)));
    return reminderConfigList;
  }

  public List<ReminderConfig> getMedNotTakenConfig() {
    List<ReminderConfig> reminderConfigList = new ArrayList<>();
    String query = remindersQuery.getMedNotTakenQuery();
    runRawQuery(query, cursor -> reminderConfigList.add(remindersOrm.getReminderConfig(cursor)));
    return reminderConfigList;
  }

  private ReminderConfig getCommonReminderConfig(Cursor c) {
    ReminderConfig reminderConfig = new ReminderConfig();
    reminderConfig.setReminderId(c.getString(c.getColumnIndex(TrakrContract.Reminders._ID)));
    reminderConfig.setDosage(
        c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_DOSAGE)));
    reminderConfig.setReminderText(
        c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_REMINDER_TEXT)));
    reminderConfig.setReminderTime(
        new Date(c.getLong(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_REMINDER_TIME))));
    reminderConfig.setRecurring(
        c.getInt(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_RECURRING)));
    reminderConfig.setIntentId(
        c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_INTENT_ID)));
    return reminderConfig;
  }
}
