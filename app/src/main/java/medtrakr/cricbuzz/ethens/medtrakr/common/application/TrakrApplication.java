package medtrakr.cricbuzz.ethens.medtrakr.common.application;

import android.app.Application;
import java.util.HashSet;
import java.util.Set;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;

/**
 * Created by ethens on 01/09/17.
 */

public class TrakrApplication extends Application {
  private Set<Integer> intentSet;

  @Override public void onCreate() {
    super.onCreate();
    ComponentFactory.getInstance().initializeComponent(this);
  }

  /**
   * returns the set of the response codes of the actions performed by the user in app and refresh
   * the dashboard according to the values of the response codes.
   *
   * @return Set of modules to be refreshed
   */
  public Set<Integer> getIntentSet() {
    if (intentSet == null) intentSet = new HashSet<>();
    return intentSet;
  }

  public void setIntentSet(Set<Integer> intentSet) {
    this.intentSet = intentSet;
  }
}
