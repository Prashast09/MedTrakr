package medtrakr.cricbuzz.ethens.medtrakr.di.database;

/**
 * Created by ethens on 01/09/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import dagger.Module;
import dagger.Provides;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.database.DBHelper;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarScope;

@Module public class DatabaseModule {

  @Provides @CalendarScope SQLiteDatabase providesDatabase(Context context) {
    String dbName = "trakr" + ".db";
    int dbVersion = context.getResources().getInteger(R.integer.DATABASE_VERSION);
    SQLiteDatabase database = new DBHelper(context, dbName, dbVersion).getWritableDatabase();
    database.enableWriteAheadLogging();
    return database;
  }
}
