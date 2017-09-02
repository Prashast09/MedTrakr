package medtrakr.cricbuzz.ethens.medtrakr.activity.statistics;

import android.os.Bundle;
import android.view.View;
import de.greenrobot.event.EventBus;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.activity.common.BaseActivity;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;

/**
 * Created by ethens on 02/09/17.
 */

public class StatisticsActivity extends BaseActivity {

  @Override protected void setupViewHolder(View view) {
    new StatisticsViewHolder(view);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI(R.layout.activity_statistics, R.id.parent_coordinator_layout);
  }

  @Override public void setupComponent() {
    ComponentFactory.getInstance().getCalendarComponent().inject(this);
  }




}
