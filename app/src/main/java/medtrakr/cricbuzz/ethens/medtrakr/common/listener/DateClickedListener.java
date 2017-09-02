package medtrakr.cricbuzz.ethens.medtrakr.common.listener;

/**
 * Created by ethens on 01/09/17.
 */

public interface DateClickedListener {

  void onDateClick(int day, int month, int year, boolean lastDate, String monthName);

  void onMonthChangeClick();
}
