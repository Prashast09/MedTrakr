package medtrakr.cricbuzz.ethens.medtrakr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.eventbus.CalendarNavigationEvents;

/**
 * Adapter for reminder cards in the bottom list of the CalendarActivity
 */

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarCardViewHolder> {

  private List<ReminderConfig> mConfigList;

  public CalendarListAdapter(List<ReminderConfig> reminderList) {
    mConfigList = reminderList == null ? new ArrayList<>() : reminderList;
  }

  @Override public CalendarCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
    CalendarCardViewHolder holder = new CalendarCardViewHolder(v);
    holder.attachListener(this::onReminderCardClick);
    return holder;
  }

  @Override public void onBindViewHolder(CalendarCardViewHolder holder, final int position) {
    ReminderConfig reminderConfig = mConfigList.get(position);
    holder.prepareReminderCard(reminderConfig);
  }

  @Override public int getItemCount() {
    return mConfigList.size();
  }

  public void setData(List<ReminderConfig> reminderConfigs) {
    this.mConfigList = reminderConfigs;
    notifyDataSetChanged();
  }

  private void onReminderCardClick(CalendarCardViewHolder holder) {
    EventBus.getDefault()
        .post(new CalendarNavigationEvents.ReminderCardClickEvent(
            mConfigList.get(holder.getAdapterPosition())));
  }
}

