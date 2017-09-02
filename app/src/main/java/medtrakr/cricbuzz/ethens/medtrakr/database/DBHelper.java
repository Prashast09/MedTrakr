package medtrakr.cricbuzz.ethens.medtrakr.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;

import static medtrakr.cricbuzz.ethens.medtrakr.database.Schema.SQL_CREATE_REMINDERS;

/**
 * Created by ethens on 02/09/17.
 */
public class DBHelper extends SQLiteOpenHelper {

  public DBHelper(Context context, String databaseName, int databaseVersion) {
    super(context, databaseName, null, databaseVersion);
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_REMINDERS);
  }

  @Override public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
  }
}
