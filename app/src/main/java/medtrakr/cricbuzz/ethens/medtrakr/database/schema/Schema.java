package medtrakr.cricbuzz.ethens.medtrakr.database.schema;

import medtrakr.cricbuzz.ethens.medtrakr.database.contract.TrakrContract;

/**
 * Created by ethens on 02/09/17.
 */

public class Schema {
  public static final String TEXT_TYPE = " TEXT";
  public static final String INTEGER_TYPE = " INTEGER";
  private static final String COMMA_SEP = ",";
  private static final String PRIMARY_KEY = " PRIMARY KEY";
  private static final String AUTOINCREMENT = " AUTOINCREMENT";
  private static final String CREATE_TABLE = "CREATE TABLE ";

  public static final String SQL_CREATE_REMINDERS = CREATE_TABLE
      + TrakrContract.Reminders.TABLE_NAME
      + " ("
      + TrakrContract.Reminders._ID
      + INTEGER_TYPE
      + PRIMARY_KEY
      + AUTOINCREMENT
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_REMINDER_TIME
      + INTEGER_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_REMINDER_TEXT
      + TEXT_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_DOSAGE
      + TEXT_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_RECURRING
      + INTEGER_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_STATUS
      + TEXT_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_INTENT_ID
      + TEXT_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_DATE
      + TEXT_TYPE
      + COMMA_SEP
      + TrakrContract.Reminders.COLUMN_NAME_MED_TAKEN
      + TEXT_TYPE
      + " )";
}
