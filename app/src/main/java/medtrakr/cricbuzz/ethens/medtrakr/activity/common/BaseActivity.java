package medtrakr.cricbuzz.ethens.medtrakr.activity.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import medtrakr.cricbuzz.ethens.medtrakr.R;

/**
 * Created by ethens on 01/09/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //ComponentFactory.getInstance().getDashboardComponent(this).inject(this);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  protected void setupUI(int layoutId, int viewHolderId) {
    setContentView(layoutId);
    setupComponent();
    getIntents();
    setupViewHolder(findViewById(viewHolderId));
  }

  public void getIntents() {

  }

  abstract protected void setupViewHolder(View view);

  protected void initializeToolbar(String title) {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(title);
    toolbar.setTitleTextColor(0xffffffff);
  }
  public void setupComponent() {
    //ComponentFactory.getInstance().getDashboardComponent(this).inject(this);
  }

}
