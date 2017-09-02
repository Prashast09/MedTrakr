package medtrakr.cricbuzz.ethens.medtrakr.di;

import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.common.application.TrakrApplication;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarComponent;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarModule;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.ReminderDaoMOdule;
import medtrakr.cricbuzz.ethens.medtrakr.di.database.DatabaseModule;

/**
 * Created by ethens on 01/09/17.
 */

public class ComponentFactory {

  public static ComponentFactory componentFactory;

  public TrakrComponent trakrComponent;
  public CalendarComponent calendarComponent;

  public static ComponentFactory getInstance() {
    if (componentFactory == null) {
      componentFactory = new ComponentFactory();
    }

    return componentFactory;
  }

  public ComponentFactory initializeComponent(TrakrApplication trakrApplication) {
    trakrComponent = DaggerTrakrComponent.builder()
        // This also corresponds to the name of your module: %component_name%Module
        .appModule(new AppModule(trakrApplication)).build();
    return componentFactory;
  }

  public TrakrComponent getTrakrComponent(){
    return trakrComponent;
  }

  /**
   * For calendar activity
   * @return Calendar Component
   */
  public CalendarComponent getCalendarComponent() {
    if (calendarComponent == null) {
      calendarComponent =
          getTrakrComponent().plus(new ReminderDaoMOdule(), new DatabaseModule());
    }
    return calendarComponent;
  }


  public void removeCalendarComponent() {
    calendarComponent = null;
  }

}
