package medtrakr.cricbuzz.ethens.medtrakr.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.BaseCallback;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.ParameterCallback;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.ReturnCallback;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;

public class BaseDao {

    public static final String TAG = BaseDao.class.getSimpleName();
    @Inject
    protected SQLiteDatabase writeableDatabase;
    @Inject
    protected CursorHelper cursorHelper;

    public void runRawQuery(String query, ParameterCallback<Cursor> callback) {
        try {
            Cursor c = getWriteableDatabase().rawQuery(query, null);
            cursorHelper.iterateCursor(c, callback);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
        }
    }

    public <T> T getFirstRecord(String query, BaseCallback<T, Cursor> callback) {
        try {
            Cursor cursor = getWriteableDatabase().rawQuery(query, null);
            return cursorHelper.getFirstEntry(cursor, callback);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public SQLiteDatabase getWriteableDatabase() {
        if (!writeableDatabase.isOpen()) {
            ComponentFactory.getInstance().removeCalendarComponent();
            ComponentFactory.getInstance().getCalendarComponent().inject(this);
        }
        return writeableDatabase;
    }

    public long executeBatchQuery(ReturnCallback<Long> batchFunction) {
        long rowsAffectd = 0;
        try {
            // requires permission
            getWriteableDatabase().beginTransaction();
            rowsAffectd = batchFunction.onResponse();
            getWriteableDatabase().setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "executeBatchQuery: ", e);
        } finally {
            try {
                getWriteableDatabase().endTransaction();
            } catch (Exception e) {
                Log.e("BaseDao", e.toString());
            }
        }
        return rowsAffectd;
    }

}
