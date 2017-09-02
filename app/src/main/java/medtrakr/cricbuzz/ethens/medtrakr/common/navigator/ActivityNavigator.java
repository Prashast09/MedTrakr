package medtrakr.cricbuzz.ethens.medtrakr.common.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.reminder.AddReminderActivity;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;

/**
 * Created by ethens on 01/09/17.
 */

public class ActivityNavigator {

  @Inject public ActivityNavigator() {

  }

  public void openReminderDetailActivity(CalendarActivity calendarActivity,
      ReminderConfig reminderConfig) {
    if (reminderConfig != null) {
      startActivity(calendarActivity, AddReminderActivity.class,
          getReminderDetailBundle(reminderConfig));
    } else {
      startActivity(calendarActivity, AddReminderActivity.class);
    }
  }

  public void startActivity(Activity context, Class targetActivity) {
    Intent intent = new Intent(context, targetActivity);
    context.startActivity(intent);
  }

  protected Bundle getReminderDetailBundle(ReminderConfig reminderConfig) {
    Bundle bundle = new Bundle();
    bundle.putParcelable("reminder_config", reminderConfig);
    return bundle;
  }

  protected void startActivity(Activity context, Class targetActivity, Bundle extras) {
    Intent intent = new Intent(context, targetActivity);
    if (context instanceof BaseActivity) {
      intent.putExtra("source", context.getClass().getSimpleName());
    }
    intent.putExtras(extras);
    context.startActivity(intent);
  }
}
