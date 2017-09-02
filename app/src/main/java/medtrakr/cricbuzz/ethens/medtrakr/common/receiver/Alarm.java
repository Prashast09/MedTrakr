package medtrakr.cricbuzz.ethens.medtrakr.common.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import java.util.Calendar;
import java.util.Set;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarActivity;
import medtrakr.cricbuzz.ethens.medtrakr.common.application.TrakrApplication;
import medtrakr.cricbuzz.ethens.medtrakr.common.notification.TrakrNotificationBuilder;

/**
 * Created by ethens on 02/09/17.
 */

public class Alarm extends BroadcastReceiver {

  private static final String TAG = Alarm.class.getSimpleName();

  @Override public void onReceive(Context context, Intent arg1) {
    showNotification(context, arg1);
    Set<Integer> intentSet = ((TrakrApplication) context.getApplicationContext()).getIntentSet();
    intentSet.add(context.getResources().getInteger(R.integer.RESPONSE_DELETE_REMINDER));
    ((TrakrApplication) context.getApplicationContext()).setIntentSet(intentSet);
  }

  private void showNotification(final Context context, final Intent intent) {
    String action = intent.getAction();
    boolean isRecurring =
        intent.getExtras().getBoolean(("recurring"), false);
    Log.d(TAG + "Receiver", "Broadcast received: " + action);

    displayNotification(context,
          intent.getStringExtra("message"), intent, isRecurring);

  }

  /**
   * @param context Context
   * @param message Notification message
   * @param receivedIntent Intent
   * @param isRecurring Recurring Reminder
   */
  private void displayNotification(final Context context,
      final String message, Intent receivedIntent, boolean isRecurring) {

    Intent intent = new Intent(context, CalendarActivity.class);
    intent.putExtra(context.getString(R.string.ALARM), true);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    intent.setAction(context.getString(R.string.ACTION_STRING) + System.currentTimeMillis());

    TrakrNotificationBuilder.buildNotification(context, intent,
        "Reminder to take Medicine", message, 1 ,true);

    if (isRecurring) {
      Calendar calendar = Calendar.getInstance();
      if (calendar.get(Calendar.MONTH) != 12) {
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
      } else {
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
      }
      int intentId = (int) System.currentTimeMillis();
      PendingIntent pi = PendingIntent.getBroadcast(context, intentId, receivedIntent,
          PendingIntent.FLAG_UPDATE_CURRENT);
      AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
      am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }
  }
}