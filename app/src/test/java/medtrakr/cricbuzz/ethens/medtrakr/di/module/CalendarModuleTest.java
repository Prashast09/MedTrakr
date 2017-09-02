package medtrakr.cricbuzz.ethens.medtrakr.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarModule;
import medtrakr.cricbuzz.ethens.medtrakr.di.calendar.CalendarScope;

/**
 * Created by ethens on 02/09/17.
 */
@Module
public class CalendarModuleTest  extends CalendarModule{
  BaseActivity mBaseActivity;
  public CalendarModuleTest(CalendarActivity calendarActivity) {
    super(calendarActivity);
  }

  @CalendarScope @Provides @Named("activityContext") BaseActivity providesResources() {
    return mBaseActivity;
  }
}
