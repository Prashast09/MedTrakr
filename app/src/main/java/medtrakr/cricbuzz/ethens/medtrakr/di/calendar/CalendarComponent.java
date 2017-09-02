package medtrakr.cricbuzz.ethens.medtrakr.di.calendar;

import dagger.Subcomponent;
import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarViewHolder;
import medtrakr.cricbuzz.ethens.medtrakr.activity.reminder.AddReminderActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.reminder.AddReminderViewHolder;
import medtrakr.cricbuzz.ethens.medtrakr.activity.statistics.StatisticsActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.statistics.StatisticsViewHolder;
import medtrakr.cricbuzz.ethens.medtrakr.adapter.CalendarCardViewHolder;
import medtrakr.cricbuzz.ethens.medtrakr.database.dao.BaseDao;
import medtrakr.cricbuzz.ethens.medtrakr.database.DBHelper;
import medtrakr.cricbuzz.ethens.medtrakr.di.database.DatabaseModule;

/**
 * Created by earthshaker on 9/26/2016.
 */
@CalendarScope
@Subcomponent(modules = { ReminderDaoMOdule.class, DatabaseModule.class, CalendarModule.class })
public interface CalendarComponent {
  void inject(CalendarViewHolder calendarViewHolder);

  void inject(CalendarActivity calendarActivity);

  void inject(CalendarCardViewHolder calendarCardViewHolder);

  void inject(DBHelper dbHelper);

  void inject(AddReminderActivity addReminderActivity);

  void inject(AddReminderViewHolder addReminderViewHolder);

  void inject(BaseDao baseDao);

  void inject(StatisticsActivity statisticsActivity);

  void inject(StatisticsViewHolder statisticsViewHolder);
}
