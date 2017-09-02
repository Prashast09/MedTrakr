package medtrakr.cricbuzz.ethens.medtrakr.di.module;

import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.database.ReminderInfoDao;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarScope;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.ReminderDaoMOdule;

/**
 * Created by ethens on 02/09/17.
 */

@Module
public class ReminderDaoModuleTest extends ReminderDaoMOdule {

  @Provides @CalendarScope @Named("allReminders")
  /**
   * @return List of reminders
   */
  List<ReminderConfig> getReminders(ReminderInfoDao reminderInfoDao) {
    return reminderInfoDao.getAllReminders();
  }
}
