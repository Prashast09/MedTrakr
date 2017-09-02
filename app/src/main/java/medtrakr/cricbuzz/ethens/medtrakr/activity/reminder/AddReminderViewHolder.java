package medtrakr.cricbuzz.ethens.medtrakr.activity.reminder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import de.greenrobot.event.EventBus;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.common.constants.DateFormatterConstants;
import medtrakr.cricbuzz.ethens.medtrakr.common.lambda.TwoParamReturn;
import medtrakr.cricbuzz.ethens.medtrakr.common.widget.CustomDateEditText;
import medtrakr.cricbuzz.ethens.medtrakr.common.widget.CutomTimeEditText;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.AppCommonEvent;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.BaseHolderEventBus;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.CalendarNavigationEvents;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.ReminderModificationEvent;
import medtrakr.cricbuzz.ethens.medtrakr.utils.ReminderHelper;

/**
 * Created by ethens on 02/09/17.
 */

public class AddReminderViewHolder extends BaseHolderEventBus {

  @Inject ReminderHelper reminderHelper;
  @Inject Context mContext;

  private LinearLayout save, delete;
  private EditText repeatReminderEditText;
  private View buttonSeparator;

  private CustomDateEditText reminderDate;
  private CutomTimeEditText reminderTime;
  private EditText reminderText, dosage;
  private RadioGroup radioGroup;
  RadioButton medTaken, medNotTaken;
  // Global Config and Other Variables
  private ReminderConfig mReminderConfig;
  private Boolean isEditing;
  private Date selectedDateInCalendar;
  private BaseActivity mBaseActivity;

  AddReminderViewHolder(BaseActivity baseActivity, View view, ReminderConfig config) {
    //Initializing Model
    this.mBaseActivity = baseActivity;
    if (config == null) {
      isEditing = false;
      mReminderConfig = new ReminderConfig();
    } else {
      isEditing = true;
      mReminderConfig = config;
    }
    selectedDateInCalendar = new Date();

    //Initializing View variables
    reminderText = (EditText) view.findViewById(R.id.et_reminder_text);
    dosage = (EditText) view.findViewById(R.id.et_med_dosage);
    reminderDate = (CustomDateEditText) view.findViewById(R.id.et_reminder_date);
    reminderTime = (CutomTimeEditText) view.findViewById(R.id.et_reminder_time);
    repeatReminderEditText = (EditText) view.findViewById(R.id.repeat_text);
    save = (LinearLayout) view.findViewById(R.id.save_button_linearlay);
    delete = (LinearLayout) view.findViewById(R.id.delete_button_linearlay);
    buttonSeparator = view.findViewById(R.id.separator);
    radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
    medTaken = (RadioButton) view.findViewById(R.id.radio_taken);
    medNotTaken = (RadioButton) view.findViewById(R.id.radio_not_taken);

    setupComponent();
    setDataToView();
    setVisibilityForAddReminder();
    setListeners();
  }

  private void setupComponent() {
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
  }

  void deleteReminder() {
    reminderHelper.deleteReminder(mReminderConfig);
  }

  /**
   * Sets data to the views
   */
  private void setDataToView() {
    if (isEditing) {
      reminderText.setText(mReminderConfig.getReminderText());
      dosage.setText(mReminderConfig.getDosage());
      reminderDate.setText(
          DateFormatterConstants.reminderDateFormat.format(mReminderConfig.getReminderTime()));
      reminderTime.setText(
          DateFormatterConstants.reminderTimeFormat.format(mReminderConfig.getReminderTime()));
      repeatReminderEditText.setText(
          reminderHelper.getReminderRepeatText(mReminderConfig.getRecurringType()));
      Boolean medicineTaken = mReminderConfig.getMedTaken();
      medTaken.setChecked(medicineTaken);
      medNotTaken.setChecked(!medicineTaken);

    } else {
        reminderDate.setText(
            DateFormatterConstants.reminderDateFormat.format(new Date()));

        repeatReminderEditText.setText(reminderHelper.getReminderRepeatText(
            mContext.getResources().getInteger(R.integer.RECURRING_NONE)));
    }
  }

  /**
   * Sets visibility to the views.
   */
  private void setVisibilityForAddReminder() {
    reminderDate.setColor(ContextCompat.getColor(mContext, R.color.calendar_background_dark_color));
    reminderTime.setColor(ContextCompat.getColor(mContext, R.color.calendar_background_dark_color));
    if (isEditing) {
      delete.setVisibility(View.VISIBLE);
    } else {
      delete.setVisibility(View.GONE);
      buttonSeparator.setVisibility(View.GONE);
      radioGroup.setVisibility(View.GONE);

    }
  }

  private void setListeners() {

    repeatReminderEditText.setOnClickListener(view -> reminderHelper.setupDialog(mBaseActivity));

    save.setOnClickListener(view -> {
      saveMyData();
    });

    delete.setOnClickListener(view -> {
      EventBus.getDefault().post(new ReminderModificationEvent.ReminderDeleteEvent());
    });
  }

  public void onEventMainThread(CalendarNavigationEvents.DialogItemSelectionEvent event) {
    repeatReminderEditText.setText(event.getText());
    mReminderConfig.setRecurring(event.getData());
  }

  private void saveMyData() {
    prepareConfigForSave();
    if (!isEditing) {
      int intentId = (int) System.currentTimeMillis();
      if (intentId < 0) intentId *= -1;
      mReminderConfig.setIntentId(String.valueOf(intentId));
    }
    if (reminderHelper.validateData(mReminderConfig)) {
      reminderHelper.saveReminder(mReminderConfig, isEditing);
      EventBus.getDefault().post(new AppCommonEvent.ReminderModifiedEvent());

    }
  }

  private void prepareConfigForSave() {
    mReminderConfig.setReminderText(reminderText.getText().toString());
    TwoParamReturn<String, String> concatDateAndTimeReturn =
        new TwoParamReturn<>(reminderDate.getText().toString(), reminderTime.getText().toString());
    Calendar calendar = reminderHelper.setCalendarTime(concatDateAndTimeReturn);
    mReminderConfig.setReminderTime(calendar.getTime());
    mReminderConfig.setDosage(dosage.getText().toString());
    Boolean medicineTaken = medTaken.isChecked();
    mReminderConfig.setMedTaken(medicineTaken );
  }

  @Override protected void refreshData() {

  }

  @Override protected void recreateLayout() {

  }
}