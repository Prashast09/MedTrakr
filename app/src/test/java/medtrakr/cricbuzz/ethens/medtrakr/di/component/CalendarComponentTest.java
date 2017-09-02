package medtrakr.cricbuzz.ethens.medtrakr.di.component;

import dagger.Subcomponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarComponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarScope;
import medtrakr.cricbuzz.ethens.medtrakr.di.database.DatabaseModule;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.ReminderDaoModuleTest;

/**
 * Created by ethens on 02/09/17.
 */
@CalendarScope @Subcomponent(modules = {
    DatabaseModule.class, ReminderDaoModuleTest.class, CalendarComponentTest.class
}) public interface CalendarComponentTest extends CalendarComponent {
}
