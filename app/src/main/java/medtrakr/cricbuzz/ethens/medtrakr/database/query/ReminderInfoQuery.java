package medtrakr.cricbuzz.ethens.medtrakr.database.query;

import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.database.TrakrContract;

/**
 * Created by ethens on 02/09/17.
 */

public class ReminderInfoQuery {

  @Inject ReminderInfoQuery() {
  }


  public String getReminderForId(long reminderId) {
    return "select * from "
        + TrakrContract.Reminders.TABLE_NAME
        + " where "
        + TrakrContract.Reminders._ID
        + " = "
        + reminderId;
  }

  public String getAllReminders() {
    return "select * from "
        + TrakrContract.Reminders.TABLE_NAME
        + " where "
        + TrakrContract.Reminders.COLUMN_NAME_STATUS
        + " = 'ACTIVE'";
  }

  public String getMedTakenQuery() {

    return "select * from "
        + TrakrContract.Reminders.TABLE_NAME
        + " where "
        + TrakrContract.Reminders.COLUMN_NAME_STATUS
        + " = 'ACTIVE' and " + TrakrContract.Reminders.COLUMN_NAME_MED_TAKEN + " = '1'";
  }

  public String getMedNotTakenQuery() {

    return "select * from "
        + TrakrContract.Reminders.TABLE_NAME
        + " where "
        + TrakrContract.Reminders.COLUMN_NAME_STATUS
        + " = 'ACTIVE' and " + TrakrContract.Reminders.COLUMN_NAME_MED_TAKEN + " = '0'";
  }
}

