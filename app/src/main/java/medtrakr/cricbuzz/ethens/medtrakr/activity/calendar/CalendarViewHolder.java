package medtrakr.cricbuzz.ethens.medtrakr.activity.calendar;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.greenrobot.event.EventBus;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Named;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.adapter.CalendarListAdapter;
import medtrakr.cricbuzz.ethens.medtrakr.common.constants.DateFormatterConstants;
import medtrakr.cricbuzz.ethens.medtrakr.common.listener.DateClickedListener;
import medtrakr.cricbuzz.ethens.medtrakr.common.widget.DrawCal;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.BaseHolderEventBus;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.CalendarNavigationEvents;
import medtrakr.cricbuzz.ethens.medtrakr.utils.ReminderHelper;

/**
 * Created by ethens on 01/09/17.
 */

public class CalendarViewHolder extends BaseHolderEventBus {

  @Inject @Named("allReminders") List<ReminderConfig> allReminders;
  @Inject ReminderHelper reminderHelper;
  @Inject Context context;

  private RecyclerView remindersRecyclerView;
  private FloatingActionButton addReminderFAB;
  private DrawCal calendar;
  private CalendarListAdapter calendarListAdapter;
  private TextView reminderStatusTextView;
  private LinearLayout noDataLayout;
  private Date currentlySelectedDate;

  CalendarViewHolder(View view) {
    calendar = (DrawCal) view.findViewById(R.id.calLayout);
    remindersRecyclerView = (RecyclerView) view.findViewById(R.id.rv_reminder_list);
    addReminderFAB = (FloatingActionButton) view.findViewById(R.id.fab_add_reminder);
    reminderStatusTextView = (TextView) view.findViewById(R.id.tv_reminder_status);
    noDataLayout = (LinearLayout) view.findViewById(R.id.no_data_layout);

    setupComponent();
    setDataToView();
    setListeners();
  }

  /**
   * Sets Config List to Calendar
   */

  public void refreshData() {
    setupComponent();
    setRecyclerViewList(false);
    setDataToCalendar();
  }

  @Override protected void recreateLayout() {

  }

  public void setDataToView() {
    //Initially, no date will be selected and therefore we need a new date to set the recyclerView on.
    if (currentlySelectedDate == null) {
      currentlySelectedDate = new Date();
    }
    setRecyclerViewList(false);
    setDataToCalendar();
  }

  private void setupComponent() {
    ComponentFactory.getInstance().removeCalendarComponent();
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
  }

  /**
   * Sets Listeners.
   */
  private void setListeners() {

    addReminderFAB.setOnClickListener(view -> EventBus.getDefault()
        .post(new CalendarNavigationEvents.ReminderCardClickEvent(null)));

    //Set remindersRecyclerView recycler view based on date selected
    calendar.setDateClickListener(new DateClickedListener() {
      @Override
      public void onDateClick(int day, int month, int year, boolean lastDate, String monthName) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0);
        currentlySelectedDate = c.getTime();
        setRecyclerViewList(lastDate);
      }

      @Override public void onMonthChangeClick() {
        //reminderStatusTextView.setText(R.string.calendar_critical_dates);
        calendarListAdapter.setData(null);
      }
    });

    calendar.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT < 16) {
              //noinspection deprecation
              calendar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              calendar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
            if (currentlySelectedDate == null) {
              currentlySelectedDate = new Date();
            }
            invalidateCalendarAndSetDay();
          }
        });
  }

  /**
   * This function sets the recyclerView List for the currently selected date.
   *
   * @param lastDate if it is the last date (from remindersDao : // for eg: if user sets 31st of
   * January as a reminder-
   * it sets repetition for 28th feb, 29th feb, 30th april)
   */
  private void setRecyclerViewList(boolean lastDate) {
    List<ReminderConfig> reminderConfigList =
        reminderHelper.getReminderForDate(allReminders, currentlySelectedDate, lastDate);
    setAdapters(reminderConfigList);
    buildRecyclerViewList();
  }

  private void setDataToCalendar() {
    invalidateCalendarAndSetDay();
    //Initialize the calendar widget with dates
    calendar.setDateList(allReminders);
  }

  /**
   * This function sets the text above the recycler view.
   *
   * @param listOfReminders ReminderConfig
   */
  private void setHeaderTextAboveRecyclerView(List<ReminderConfig> listOfReminders) {
    if (listOfReminders == null || listOfReminders.size() == 0) {
      reminderStatusTextView.setText(context.getString(R.string.calendar_no_reminder));
    } else {
      reminderStatusTextView.setText(
          String.format("%s %s", String.format(Locale.getDefault(), "%d", listOfReminders.size()),
              context.getString(R.string.calendar_reminders)));
    }
  }

  /**
   * This function sets the adapter for the the recycler view below the calendar
   *
   * @param lReminders list of Reminders
   */
  private void setAdapters(List<ReminderConfig> lReminders) {
    if (calendarListAdapter == null) {
      calendarListAdapter = new CalendarListAdapter(lReminders);
    } else {
      calendarListAdapter.setData(lReminders);
    }
    if (lReminders.size() == 0) {
      remindersRecyclerView.setVisibility(View.GONE);
      noDataLayout.setVisibility(View.VISIBLE);
      reminderStatusTextView.setVisibility(View.GONE);
    } else {
      remindersRecyclerView.setVisibility(View.VISIBLE);
      noDataLayout.setVisibility(View.GONE);
      reminderStatusTextView.setVisibility(View.VISIBLE);
    }
    setHeaderTextAboveRecyclerView(lReminders);
  }

  /**
   * This function attaches the adapter to the recyclerView.
   */
  private void buildRecyclerViewList() {
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    remindersRecyclerView.setLayoutManager(layoutManager);
    remindersRecyclerView.setAdapter(calendarListAdapter);
  }

  /**
   * Invalidates the calendar selection and selects the date 'currentlySelectedDate'
   */
  private void invalidateCalendarAndSetDay() {
    calendar.invalidate();
    calendar.setSelectedDay(Integer.valueOf(DateFormatterConstants.
        dayFormatter.format(currentlySelectedDate)));
  }
}
