package medtrakr.cricbuzz.ethens.medtrakr.activity.calendar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import de.greenrobot.event.EventBus;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.statistics.StatisticsActivity;
import medtrakr.cricbuzz.ethens.medtrakr.common.navigator.ActivityNavigator;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.CalendarNavigationEvents;

/**
 * Created by ethens on 01/09/17.
 */

public class CalendarActivity extends BaseActivity {

  @Inject ActivityNavigator activityNavigator;
  private CalendarViewHolder calendarViewHolder;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI(R.layout.activity_calender, R.id.parent_coordinator_layout);
    EventBus.getDefault().register(this, getResources().getInteger(R.integer.level_0));
  }

  @Override public void setupComponent() {
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override protected void onResume() {
    super.onResume();
    refreshData();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu_calendar, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_open_statistics:
        activityNavigator.startActivity(this, StatisticsActivity.class);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void setupViewHolder(View view) {
    calendarViewHolder = new CalendarViewHolder(view);
  }

  public void onEventMainThread(CalendarNavigationEvents.ReminderCardClickEvent event) {
    activityNavigator.openReminderDetailActivity(this, event.getReminderConfig());
  }

  protected void refreshData() {
    calendarViewHolder.refreshData();
  }
}
