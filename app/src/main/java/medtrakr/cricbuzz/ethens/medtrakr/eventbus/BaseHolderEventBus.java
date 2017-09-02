package medtrakr.cricbuzz.ethens.medtrakr.eventbus;

import de.greenrobot.event.EventBus;

/**
 * Created by ethens on 01/09/17.
 */

public abstract class BaseHolderEventBus {

  public void registerEventBus(int priority) {
    EventBus.getDefault().register(this, priority);
  }

  public void unRegisterEventBus() {
    EventBus.getDefault().unregister(this);
  }

  protected abstract void refreshData();

  protected abstract void recreateLayout();
}
