package medtrakr.cricbuzz.ethens.medtrakr.di.calendar;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import medtrakr.cricbuzz.ethens.medtrakr.activity.calendar.CalendarActivity;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;

/**
 * Created by ethens on 01/09/17.
 */

@Module public class CalendarModule {

  BaseActivity mBaseActivity;

  public CalendarModule(CalendarActivity calendarActivity) {
    this.mBaseActivity = calendarActivity;
  }

  @CalendarScope @Provides @Named("activityContext") BaseActivity providesResources() {
    return mBaseActivity;
  }
}
