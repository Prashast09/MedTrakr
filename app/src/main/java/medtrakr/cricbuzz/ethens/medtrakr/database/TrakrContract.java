package medtrakr.cricbuzz.ethens.medtrakr.database;

/**
 * Created by ethens on 02/09/17.
 */

public class TrakrContract {

  public TrakrContract() {

  }

  public static abstract class Reminders {
    public static final String TABLE_NAME = "reminder";
    public static final String _ID = "_id";
    public static final String COLUMN_NAME_REMINDER_TIME = "reminderTime";
    public static final String COLUMN_NAME_REMINDER_TEXT = "reminderText";
    public static final String COLUMN_NAME_DOSAGE = "dosage";
    public static final String COLUMN_NAME_RECURRING = "recurring";
    public static final String COLUMN_NAME_STATUS = "status";
    public static final String COLUMN_NAME_INTENT_ID = "intentId";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_MED_TAKEN = "medTaken";
  }
}
