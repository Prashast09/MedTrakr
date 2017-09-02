package medtrakr.cricbuzz.ethens.medtrakr.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import medtrakr.cricbuzz.ethens.medtrakr.common.application.TrakrApplication;

/**
 * Created by ethens on 01/09/17.
 */
@Module
public class AppModule {

    public TrakrApplication trakrApplication;

    public AppModule(TrakrApplication trakrApplication) {
        this.trakrApplication = trakrApplication;
    }

    @Provides
    @Singleton
    TrakrApplication providesProductApplication() {
        return trakrApplication;
    }

    @Provides
    @Singleton Application providesApplication() {
        return trakrApplication;
    }

    @Provides
    @Singleton SharedPreferences providesSharedPrefs() {
        return PreferenceManager.getDefaultSharedPreferences(trakrApplication);
    }

    @Provides
    @Singleton Context providesContext() {
        return trakrApplication;
    }
}
