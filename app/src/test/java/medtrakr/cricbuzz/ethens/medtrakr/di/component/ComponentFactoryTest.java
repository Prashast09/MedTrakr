package medtrakr.cricbuzz.ethens.medtrakr.di.component;

import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;
import medtrakr.cricbuzz.ethens.medtrakr.di.TrakrComponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.DatabaseModuleTest;
import medtrakr.cricbuzz.ethens.medtrakr.di.module.ReminderDaoModuleTest;

/**
 * Created by ethens on 02/09/17.
 */

public class ComponentFactoryTest extends ComponentFactory {

  public static ComponentFactoryTest componentFactoryTest;

  public TrakrComponentTest trakrComponentTest;
  public CalendarComponentTest calendarComponentTest;

  public TrakrComponent getTrakrComponent() {
    return trakrComponentTest;
  }

  /**
   * For calendar activity
   *
   * @return Calendar Component
   */
  public CalendarComponentTest getCalendarComponent() {
    if (calendarComponentTest == null) {
      calendarComponentTest =
          ((TrakrComponentTest) getTrakrComponent()).plus(new ReminderDaoModuleTest(),
              new DatabaseModuleTest());
    }
    return calendarComponentTest;
  }

  public void removeCalendarComponent() {
    calendarComponentTest = null;
  }
}