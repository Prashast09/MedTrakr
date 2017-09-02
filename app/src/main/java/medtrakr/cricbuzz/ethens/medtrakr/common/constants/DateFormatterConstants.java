package medtrakr.cricbuzz.ethens.medtrakr.common.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ethens on 01/09/17.
 */

public class DateFormatterConstants {
  public static final SimpleDateFormat reminderTimeDateFormat =
      new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.UK);

  public static final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd", Locale.UK);

  public static final SimpleDateFormat reminderDateFormat =
      new SimpleDateFormat("dd-MM-yyyy", Locale.UK);

  public static final SimpleDateFormat reminderTimeFormat =
      new SimpleDateFormat("HH:mm", Locale.UK);

  public static final SimpleDateFormat statsFormat = new SimpleDateFormat("HH", Locale.UK);

  public static final SimpleDateFormat statsDateFormat = new SimpleDateFormat("dd", Locale.UK);

  public static final SimpleDateFormat monthFormatter = new SimpleDateFormat("MMM", Locale.UK);
}
