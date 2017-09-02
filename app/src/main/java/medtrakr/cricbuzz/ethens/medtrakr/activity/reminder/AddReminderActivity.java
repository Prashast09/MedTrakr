package medtrakr.cricbuzz.ethens.medtrakr.activity.reminder;

import android.os.Bundle;
import android.view.View;
import de.greenrobot.event.EventBus;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.common.handler.TrakrHandler;
import medtrakr.cricbuzz.ethens.medtrakr.common.widget.TwoButtonDialogListener;
import medtrakr.cricbuzz.ethens.medtrakr.common.widget.TwoButtonSimpleDialog;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.AppCommonEvent;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.ReminderModificationEvent;

/**
 * Created by ethens on 02/09/17.
 */

public class AddReminderActivity extends BaseActivity {
  private ReminderConfig mReminderConfig;
  private AddReminderViewHolder addReminderViewHolder;

  public AddReminderActivity() {
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI(R.layout.activity_add_reminder, R.id.parent_coordinator_layout);
    EventBus.getDefault().register(this, getResources().getInteger(R.integer.level_1));
  }

  @Override public void setupComponent() {
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable("reminder_config", mReminderConfig);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mReminderConfig = savedInstanceState.getParcelable("reminder_config");
  }

  @Override public void getIntents() {
    super.getIntents();
    mReminderConfig = getIntent().getParcelableExtra("reminder_config");
  }

  @Override public void setupViewHolder(View view) {
    setTitle("Add Reminder");
    addReminderViewHolder = new AddReminderViewHolder(this, view, mReminderConfig);
    addReminderViewHolder.registerEventBus(getResources().getInteger(R.integer.level_1));
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    addReminderViewHolder.unRegisterEventBus();
    EventBus.getDefault().unregister(this);
  }

  public void onEventMainThread(AppCommonEvent.ReminderModifiedEvent event) {
    finish();
  }

  //delete Event
  public void onEventMainThread(ReminderModificationEvent.ReminderDeleteEvent event) {
    new TwoButtonSimpleDialog(this, "Delete this reminder",
        "Are you sure you want to delete this reminder?", new TwoButtonDialogListener() {

      @Override public void onAccept() {
        new TrakrHandler(this::deleteInBackground);
        EventBus.getDefault().post(new AppCommonEvent.ReminderModifiedEvent());
      }

      @Override public void onReject() {
      }

      void deleteInBackground() {
        addReminderViewHolder.deleteReminder();
      }
    });
  }
}
