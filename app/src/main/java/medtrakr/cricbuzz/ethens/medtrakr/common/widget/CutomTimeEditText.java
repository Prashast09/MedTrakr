package medtrakr.cricbuzz.ethens.medtrakr.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import medtrakr.cricbuzz.ethens.medtrakr.common.constants.DateFormatterConstants;
import medtrakr.cricbuzz.ethens.medtrakr.utils.StringUtils;

/**
 * Created by ethens on 02/09/17.
 */

public class CutomTimeEditText extends AppCompatEditText implements TimePickerDialog.OnTimeSetListener{

  CustomClickListener customClickListener;
  private TimePickerDialog timePicker;
  private int mAccentColor;

  public CutomTimeEditText(Context context) {
    super(context);
    setupClickListener(context);
  }

  public CutomTimeEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    setupClickListener(context);
  }

  public CutomTimeEditText(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setupClickListener(context);
  }

  private void setupClickListener(final Context context) {
    setFocusable(false);
    setClickable(false);

    setOnClickListener(view -> {
      Calendar now = Calendar.getInstance();
      if (!StringUtils.isBlank(getText().toString())) {
        try {
          Date date = DateFormatterConstants.reminderTimeFormat.parse(getText().toString());
          now.setTime(date);
        } catch (ParseException e) {
          Log.e("CutomTimeEditText", e.toString());
        }
      }
      timePicker =
          TimePickerDialog.newInstance((TimePickerDialog.OnTimeSetListener) CutomTimeEditText.this, now.get(Calendar.HOUR_OF_DAY),
              now.get(Calendar.MINUTE), false);
      if (mAccentColor != 0) {
        timePicker.setAccentColor(mAccentColor);
      }
      AppCompatActivity activity = ((AppCompatActivity)getActivity(context));
      if (activity != null) timePicker.show(activity.getFragmentManager(), "Timepickerdialog");
      if (customClickListener != null) {
        customClickListener.onCustomClick();
      }
    });
  }

  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }

  @Override public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
    String hour = String.format(Locale.UK, "%1$02d", hourOfDay);
    String minutes = String.format(Locale.UK, "%1$02d", minute);
    setText(String.format("%s:%s", hour, minutes));
  }

  public void setColor(int color) {
    mAccentColor = color;
  }

  interface CustomClickListener {
    void onCustomClick();
  }

  public static Context getActivity(Context context) {
    if (context instanceof Activity) {
      return context;
    } else if (context instanceof ContextThemeWrapper) {
      return getActivity(((ContextWrapper) context).getBaseContext());
    }
    return null;
  }
}
