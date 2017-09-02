package medtrakr.cricbuzz.ethens.medtrakr.activity.statistics;

import android.view.View;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.common.constants.DateFormatterConstants;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;
import medtrakr.cricbuzz.ethens.medtrakr.database.dao.ReminderInfoDao;
import medtrakr.cricbuzz.ethens.medtrakr.di.ComponentFactory;

/**
 * Created by ethens on 02/09/17.
 */

public class StatisticsViewHolder {

  @Inject ReminderInfoDao reminderInfoDao;

  LineChart medTakenChart, medNotTakenChart;

  public StatisticsViewHolder(View view) {
    ComponentFactory.getInstance().getCalendarComponent().inject(this);

    medNotTakenChart = (LineChart) view.findViewById(R.id.med_not_taken);
    medTakenChart = (LineChart) view.findViewById(R.id.med_taken);

    initializeDataSets();
  }

  public static int getDaysLeftInCurrentMonth() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  private void initializeDataSets() {
    List<ReminderConfig> medTakenConfig = reminderInfoDao.getMedTakenConfig();
    ArrayList<Entry> medTakenEntry = createEntryPoints(medTakenConfig);
    LineDataSet medDataSet = new LineDataSet(medTakenEntry, "Days of Month");

    List<ReminderConfig> noMedConfig = reminderInfoDao.getMedNotTakenConfig();
    ArrayList<Entry> noMedEntry = createEntryPoints(noMedConfig);
    LineDataSet noMedDataSet = new LineDataSet(noMedEntry, "Days of Month");

    ArrayList<String> labels = new ArrayList<>();
    for (int i = 1; i <= getDaysLeftInCurrentMonth(); i++) {
      labels.add("Day " + i);
    }

    medDataSet.setDrawCubic(true);
    noMedDataSet.setDrawCubic(true);

    medDataSet.setDrawFilled(true);
    noMedDataSet.setDrawFilled(true);

    showGraph(medDataSet, noMedDataSet, labels);
  }

  private void showGraph(LineDataSet medTakenEntry, LineDataSet noMedEntry,
      ArrayList<String> labels) {

    LineData medTakenLineData = new LineData(labels, medTakenEntry);
    medTakenChart.setData(medTakenLineData);
    medTakenChart.setDescription("Hours on Y-axis");

    LineData noMedTakenLineData = new LineData(labels, noMedEntry);
    medNotTakenChart.setData(noMedTakenLineData);
    medNotTakenChart.setDescription("Hours on Y-axis");
  }

  private ArrayList<Entry> createEntryPoints(List<ReminderConfig> reminderConfigs) {
    ArrayList<Entry> entryArrayList = new ArrayList<>();
    for (ReminderConfig reminderConfig : reminderConfigs) {
      int timeHour = Integer.parseInt(
          DateFormatterConstants.statsFormat.format(reminderConfig.getReminderTime()));
      int dayOfMonth = Integer.parseInt(
          DateFormatterConstants.statsDateFormat.format(reminderConfig.getReminderTime()));
      entryArrayList.add(new Entry(timeHour, dayOfMonth));
    }
    return entryArrayList;
  }
}
