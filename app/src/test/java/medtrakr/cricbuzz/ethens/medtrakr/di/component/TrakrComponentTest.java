package medtrakr.cricbuzz.ethens.medtrakr.di.component;

import dagger.Component;
import javax.inject.Singleton;
import medtrakr.cricbuzz.ethens.medtrakr.di.TrakrComponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarComponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.ReminderDaoMOdule;
import medtrakr.cricbuzz.ethens.medtrakr.di.database.DatabaseModule;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.AppModuleTest;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.CalendarModuleTest;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.DatabaseModuleTest;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.ReminderDaoModuleTest;

/**
 * Created by ethens on 02/09/17.
 */

@Singleton @Component(modules = { AppModuleTest.class }) public interface TrakrComponentTest
    extends TrakrComponent {
  CalendarComponentTest plus(ReminderDaoModuleTest reminderDaoMOdule, DatabaseModuleTest databaseModule);

}
