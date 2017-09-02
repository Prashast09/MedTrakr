package medtrakr.cricbuzz.ethens.medtrakr.di;


import dagger.Component;
import javax.inject.Singleton;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarComponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarModule;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.ReminderDaoMOdule;
import medtrakr.cricbuzz.ethens.medtrakr.di.database.DatabaseModule;

/**
 * Created by ethens on 01/09/17.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface TrakrComponent {

  CalendarComponent plus(ReminderDaoMOdule reminderDaoMOdule, DatabaseModule databaseModule);
}
