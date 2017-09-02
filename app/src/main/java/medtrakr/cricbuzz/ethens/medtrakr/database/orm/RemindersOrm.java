package medtrakr.cricbuzz.ethens.medtrakr.database.orm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.Date;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.common.constants.DateFormatterConstants;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.database.contract.TrakrContract;

/**
 * Created by ethens on 02/09/17.
 */

public class RemindersOrm {

  @Inject Context context;

  @Inject public RemindersOrm() {
  }

  public ContentValues addReminder(ReminderConfig reminderConfig, boolean update) {
    ContentValues cv = new ContentValues();
    cv.put(TrakrContract.Reminders.COLUMN_NAME_REMINDER_TEXT, reminderConfig.getReminderText());
    cv.put(TrakrContract.Reminders.COLUMN_NAME_DOSAGE, reminderConfig.getDosage());
    cv.put(TrakrContract.Reminders.COLUMN_NAME_REMINDER_TIME,
        String.valueOf(reminderConfig.getReminderTime().getTime()));
    cv.put(TrakrContract.Reminders.COLUMN_NAME_RECURRING, reminderConfig.getRecurringType());
    cv.put(TrakrContract.Reminders.COLUMN_NAME_INTENT_ID, reminderConfig.getIntentId());
    cv.put(TrakrContract.Reminders.COLUMN_NAME_MED_TAKEN, reminderConfig.getMedTaken());
    if (update) {
      cv.put(TrakrContract.Reminders.COLUMN_NAME_DATE, Long.toString((new Date()).getTime()));
    } else {
      cv.put(TrakrContract.Reminders.COLUMN_NAME_STATUS, "ACTIVE");
      cv.put(TrakrContract.Reminders.COLUMN_NAME_DATE, String.valueOf(new Date()));
    }
    return cv;
  }

  public ReminderConfig getReminderConfig(Cursor c) {
    String reminderId = c.getString(c.getColumnIndex(TrakrContract.Reminders._ID));
    String intentId = c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_INTENT_ID));
    Long reminderTime =
        c.getLong(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_REMINDER_TIME));
    String reminderText =
        c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_REMINDER_TEXT));
    String dosage = c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_DOSAGE));

    int recurring = c.getInt(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_RECURRING));

    ReminderConfig rem = new ReminderConfig();

    rem.setIntentId(intentId);
    rem.setReminderId(reminderId);
    rem.setReminderText(reminderText);
    rem.setReminderTime(new Date(reminderTime));

    Date dateMonth = rem.getReminderTime();
    String reminderMonth = DateFormatterConstants.monthFormatter.format(dateMonth);
    String reminderDate = DateFormatterConstants.dayFormatter.format(dateMonth);

    rem.setReminderDisplayDay(reminderDate);
    rem.setReminderDisplayMonth(reminderMonth);
    rem.setRecurring(recurring);
    rem.setMedTaken(
        (c.getString(c.getColumnIndex(TrakrContract.Reminders.COLUMN_NAME_MED_TAKEN))).equals("1"));
    rem.setDosage(dosage);
    return rem;
  }

  public ContentValues deleteReminder() {
    ContentValues cv = new ContentValues();
    cv.put(TrakrContract.Reminders.COLUMN_NAME_STATUS, "DELETED");
    cv.put(TrakrContract.Reminders.COLUMN_NAME_DATE, Long.toString((new Date()).getTime()));
    return cv;
  }
}
