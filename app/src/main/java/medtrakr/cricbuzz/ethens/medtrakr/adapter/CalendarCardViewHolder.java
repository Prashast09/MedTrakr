package medtrakr.cricbuzz.ethens.medtrakr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Date;
import java.util.Locale;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.ParameterCallback;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;
import java.util.Calendar;

public class CalendarCardViewHolder extends RecyclerView.ViewHolder {

  @Inject Context context;

  private TextView reminder;
  private ImageView provider;
  private TextView reminderTimeHour;
  private TextView reminderTimeMinute;
  private TextView reminderTimeMeridian;
  private LinearLayout reminderChildLayout;

  private String TAG = this.getClass().getSimpleName();

  public CalendarCardViewHolder(View itemView) {
    super(itemView);
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
    reminder = (TextView) itemView.findViewById(R.id.remind);
    provider = (ImageView) itemView.findViewById(R.id.vendor);
    reminderTimeHour = (TextView) itemView.findViewById(R.id.remindTimeHour);
    reminderTimeMinute = (TextView) itemView.findViewById(R.id.remindTimeMinute);
    reminderTimeMeridian = (TextView) itemView.findViewById(R.id.remindTimeMeridian);
    reminderChildLayout = (LinearLayout) itemView.findViewById(R.id.reminder_child_linear_layout);
  }

  /**
   * Prepares the reminderCard which is used in the CalendarActivity
   *
   * @param reminderConfig Config of the reminder to be set
   */
  public void prepareReminderCard(final ReminderConfig reminderConfig) {
    reminder.setText(reminderConfig.getReminderText());
    Date reminderDate = reminderConfig.getReminderTime();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(reminderDate);
    String hours = String.format(Locale.UK, "%1$02d", calendar.get(Calendar.HOUR));
    if (hours.equals("00")) {
      hours = "12";
    }
    reminderTimeHour.setText(hours);
    reminderTimeMinute.setText(String.format(Locale.UK, "%1$02d", calendar.get(Calendar.MINUTE)));
    reminderTimeMeridian.setText(String.valueOf(calendar.get(Calendar.MINUTE)));
    reminderTimeMeridian.setText((calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");

  }

  /**
   * Attaches Listener to Calendar Card ViewHolder
   *
   * @param callback Calendar Card ViewHolder context
   */
  public void attachListener(ParameterCallback<CalendarCardViewHolder> callback) {
    reminderChildLayout.setOnClickListener(v -> {
      callback.onResponse(this);
    });
  }
}