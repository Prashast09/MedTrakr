package medtrakr.cricbuzz.ethens.medtrakr.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;
import de.greenrobot.event.EventBus;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.common.constants.DateFormatterConstants;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.TwoParamReturn;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.database.dao.ReminderModificationDao;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.CalendarNavigationEvents;

public class ReminderHelper {

  private static final String TAG = ReminderHelper.class.getSimpleName();
  @Inject Context context;
  @Inject ReminderModificationDao reminderModificationDao;

  @Inject ReminderHelper() {
  }

  /**
   * Set the text for the Daily reminder
   *
   * @param selectedRecurring selected recurring type
   * @return integer value corresponding to it
   */
  public int getReminderRepeatText(int selectedRecurring) {
    if (selectedRecurring == context.getResources().getInteger(R.integer.RECURRING_NONE)) {
      return (R.string.reminder_repeat_cycle_none);
    } else if (selectedRecurring == context.getResources().getInteger(R.integer.RECURRING_DAILY)) {
      return (R.string.reminder_repeat_cycle_daily);
    } else if (selectedRecurring == context.getResources().getInteger(R.integer.RECURRING_WEEKLY)) {
      return (R.string.reminder_repeat_cycle_Weekly);
    } else if (selectedRecurring == context.getResources()
        .getInteger(R.integer.RECURRING_MONTHLY)) {
      return (R.string.reminder_repeat_cycle_monthly);
    } else {
      return (R.string.reminder_repeat_cycle_none);
    }
  }

  /**
   * Sets up the dialog for both dialog types below
   */
  public void setupDialog(BaseActivity baseActivity) {

    CharSequence[] strings = context.getResources().getStringArray(R.array.repeatReminderDialog);
    AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
    builder.setTitle(Html.fromHtml("<font color='#000000'>" + "Select Frequency" + "</font>"));

    builder.setItems(strings, (dialogInterface, i) -> {
      EventBus.getDefault()
          .post(new CalendarNavigationEvents.DialogItemSelectionEvent(strings[i].toString(), i));
    });
    Dialog dialog = builder.create();
    dialog.show();
  }

  /**
   * Validates the entered data.
   *
   * @param mReminderConfig Reminder under consideration.
   * @return whether to validate the reminder or not.
   */
  @SuppressWarnings("unchecked") public boolean validateData(ReminderConfig mReminderConfig) {
    if (StringUtils.isBlank(mReminderConfig.getReminderText())) {
      Toast.makeText(context, "Please enter required field", Toast.LENGTH_SHORT).show();
      return false;
    } else if (mReminderConfig.getReminderTime() == null) {
      Toast.makeText(context, "Please enter reminder Date/Time", Toast.LENGTH_SHORT).show();
      return false;
    }
    return true;
  }

  /**
   * Sets the calendar time and returns it to a calendar object
   *
   * @param dateAndTime TwoParamreturn with the first param as the date and the second as the time
   * @return Calendar object with the set time.
   */
  public Calendar setCalendarTime(TwoParamReturn<String, String> dateAndTime) {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    try {
      calendar.setTime(DateFormatterConstants.reminderTimeDateFormat.parse(
          dateAndTime.getFirstParam() + " " + dateAndTime.getSecondParam()));
    } catch (ParseException e) {
      Log.d(TAG, e.toString());
    }
    return calendar;
  }

  /**
   * Saves all reminders in database
   *
   * @param reminderConfig reminderConfig
   * @param editing is reminder editable
   */
  public void saveReminder(ReminderConfig reminderConfig, Boolean editing) {
    reminderModificationDao.addReminder(reminderConfig, editing);
  }

  /**
   * deletes selected reminder
   *
   * @param reminderConfig reminderconfig to be deleted
   */
  public void deleteReminder(ReminderConfig reminderConfig) {
    reminderModificationDao.deleteReminder(reminderConfig.getReminderId(), reminderConfig);
  }

  /**
   * returns reminder config list which are now valid upon give conditions
   *
   * @param reminderConfigList reminder config list of all reminders
   * @param date selected date in calendar
   * @param lastDate is selected date last date or not
   * @return list of reminder config list
   */
  public List<ReminderConfig> getReminderForDate(List<ReminderConfig> reminderConfigList, Date date,
      boolean lastDate) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date);
    List<ReminderConfig> reminders = new ArrayList<>();
    if (reminderConfigList != null) {
      for (ReminderConfig reminderConfig : reminderConfigList) {
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(reminderConfig.getReminderTime());
        if (cal1.get(Calendar.YEAR) <= cal2.get(Calendar.YEAR)
            && cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
          continue;
        }

        if (reminderConfig.getRecurringType() == context.getResources()
            .getInteger(R.integer.RECURRING_MONTHLY)) {
          boolean finalRecurringFilter;
          if (lastDate) {
            // for eg: if user sets 31st of January as a reminder-
            // it sets repetition for 28th feb, 29th feb, 30th april
            finalRecurringFilter =
                (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
                    || cal1.get(Calendar.DAY_OF_MONTH) + 1 == cal2.get(Calendar.DAY_OF_MONTH)
                    || cal1.get(Calendar.DAY_OF_MONTH) + 2 == cal2.get(Calendar.DAY_OF_MONTH)
                    || cal1.get(Calendar.DAY_OF_MONTH) + 3 == cal2.get(Calendar.DAY_OF_MONTH));
          } else {
            finalRecurringFilter =
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
          }
          if (finalRecurringFilter) {
            reminders.add(reminderConfig);
          }
        } else if (reminderConfig.getRecurringType() == context.getResources()
            .getInteger(R.integer.RECURRING_WEEKLY)) {
          if (cal2.get(Calendar.DAY_OF_WEEK) == cal1.get(Calendar.DAY_OF_WEEK)) {
            reminders.add(reminderConfig);
          }
        } else if (reminderConfig.getRecurringType() == context.getResources()
            .getInteger(R.integer.RECURRING_DAILY)) {
          reminders.add(reminderConfig);
        } else {
          if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
              && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
            reminders.add(reminderConfig);
          }
        }
      }
    }
    return reminders;
  }
}
