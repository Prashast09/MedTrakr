package medtrakr.cricbuzz.ethens.medtrakr.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Provides;
import javax.inject.Singleton;
import medtrakr.cricbuzz.ethens.medtrakr.common.application.TrakrApplication;
import medtrakr.cricbuzz.ethens.medtrakr.di.AppModule;

/**
 * Created by ethens on 02/09/17.
 */

public class AppModuleTest extends AppModule {
  public AppModuleTest(TrakrApplication trakrApplication) {
    super(trakrApplication);
  }

  public TrakrApplication trakrApplication;

  @Provides @Singleton TrakrApplication providesProductApplication() {
    return trakrApplication;
  }

  @Provides @Singleton Application providesApplication() {
    return trakrApplication;
  }

  @Provides @Singleton SharedPreferences providesSharedPrefs() {
    return PreferenceManager.getDefaultSharedPreferences(trakrApplication);
  }

  @Provides @Singleton Context providesContext() {
    return trakrApplication;
  }

}
