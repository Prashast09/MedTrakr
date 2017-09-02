package medtrakr.cricbuzz.ethens.medtrakr.database;

import android.database.Cursor;
import android.util.Log;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.BaseCallback;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.ParameterCallback;

public class CursorHelper {

  public static final String TAG = CursorHelper.class.getSimpleName();

  @Inject public CursorHelper() {
  }

  public <T> T getFirstEntry(Cursor c, BaseCallback<T, Cursor> callback) {
    T t = null;
    try {
      if (c != null) {
        if (c.getCount() > 0 && c.moveToFirst()) {
          try {
            t = callback.onResponse(c);
          } catch (Exception e) {
            Log.e(TAG, "iterateCursor: ", e);
          }
        }
        c.close();
      }
    } catch (Exception e) {
      c.close();
      Log.e(TAG, "iterateCursor: ", e);
    }

    return t;
  }

  public void iterateCursor(Cursor c, ParameterCallback<Cursor> callback) {
    // http://stackoverflow.com/questions/14316082/cursor-window-could-not-be-created-from-binder
    try {
      if (c
          != null) { // must close cursor regardless of the count and the condition of not affected by this way
        if (c.getCount() > 0 && c.moveToFirst()) {
          do {
            try {
              callback.onResponse(c);
            } catch (Exception e) {
              Log.e(TAG, "iterateCursor: ", e);
            }
          } while (c.moveToNext());
        }
        c.close();
      }
    } catch (Exception e) {
      c.close();
      Log.e(TAG, "iterateCursor: ", e);
    }
  }
}
