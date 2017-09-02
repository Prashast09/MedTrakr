package medtrakr.cricbuzz.ethens.medtrakr.common.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import medtrakr.cricbuzz.ethens.medtrakr.R;
import medtrakr.cricbuzz.ethens.medtrakr.common.listener.DateClickedListener;
import medtrakr.cricbuzz.ethens.medtrakr.config.ReminderConfig;

/**
 * Created by Lina on 07-09-2015.
 * Modified by james_alcatraz on 31-12-2015.
 */
public class DrawCal extends View {
    protected static final int DEFAULT_NUM_DAYS = 7;
    protected static final int DEFAULT_WEEK_START = Calendar.MONDAY;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static final int DEFAULT_SELECTED_DAY = -1;
    private static final int SELECTED_CIRCLE_ALPHA = 255;
    private static final int SWIPE_THRESHOLD = 100;
    protected static int CAL_FONT_SIZE;
    protected static int MONTH_LABEL_TEXT_SIZE;
    protected static int DAY_SELECTED_CIRCLE_SIZE;
    protected static int MAJOR_ROW_GAP;
    protected static int MINOR_ROW_GAP;
    protected static int HEADING_ROW_GAP;
    protected static int REMINDER_INDICATOR_CIRCLE_SIZE;
    protected static int DAY_LABEL_GAP;
    protected final int currentMonth;
    protected final int currentYear;
    protected final Calendar mToday = Calendar.getInstance();
    protected final int currentDay;
    protected final Calendar mDayLabel;
    protected Canvas mCanvas;
    protected boolean daySelected;
    protected int screenWidth;
    protected int screenHeight;
    protected float mSelectedCircleCanvasX;
    protected float mSelectedCircleCanvasY;
    protected float mSelectedNumCanvasX;
    protected float mSelectedNumCanvasY;
    // Quick reference to the width of this view, matches parent (adjusts to capture width according to orientation)
    protected int mWidth;
    // affects the padding on the sides of this view
    protected int mEdgePadding = 0;
    // How many days to display
    protected int mNumDays = DEFAULT_NUM_DAYS;
    // Which day of the week to start on [0-6]
    protected int mWeekStart = DEFAULT_WEEK_START;
    // The height this view should draw at in pixels, set by height param
    protected int mRowHeight;
    protected int mHeaderHeight;
    protected float mDayWidthHalf;
    protected float mGap;
    protected int mDayLabelGap;
    // The number of days + a spot for week number if it is displayed
    protected int mNumDaysInMonth = mNumDays;
    protected int mMonth;
    protected int mYear;
    protected String mMonthName;
    // Which day is selected [0-6] or -1 if no day is selected
    protected int mSelectedDay = -1;
    protected int mNumRows = DEFAULT_NUM_ROWS;
    protected Calendar calendar;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;
    protected Paint mSelectedNumPaint;
    protected Paint mTodayPaint;
    protected Paint mReminderPaint;
    protected Paint mMonthDayLabelPaint;
    protected Paint mReminderIndicatorPaint;
    protected Paint mNextMonthNumPaint;
    protected int mDayTextColor;
    protected int mMonthDayTextColor;
    protected int mSelectedCircleBgColor;
    protected int mDaySelectedTextColor;
    protected int mTodayDateColor;
    protected int mReminderIndicatorColor;
    protected int mNextMonthDateColor;
    protected float mTouchX;
    protected float mTouchY;
    protected float mTouchX2;
    private int mStartingDayOfWeek = 0;
    private List<ReminderConfig> reminderDateList;
    private Context context;

    private DateClickedListener mDateClickedListener;

    //Added by Arjun to get Coordinate of day
    //private HashMap<Integer, Coordinate> dayToCoordinate = new HashMap<>();
    private SparseArray<Coordinate> dayToCoordinate = new SparseArray<>();
    public DrawCal(Context context) {
        this(context, null, 0);
    }

//    TODO: Fix circle on top corner at beginning of reminder

    public DrawCal(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawCal(Context context, AttributeSet attr, int defStyle) {
        super(context, attr);
        //setId()
        this.context = context;
        Resources res = context.getResources();
        currentMonth = mToday.get(Calendar.MONTH);
        currentYear = mToday.get(Calendar.YEAR);
        currentDay = mToday.get(Calendar.DAY_OF_MONTH);
        mMonth = currentMonth;
        mYear = currentYear;
        mDayLabel = Calendar.getInstance();


        mDayTextColor = res.getColor(R.color.white);
        mMonthDayTextColor = res.getColor(R.color.date_picker_month_day);
        mDaySelectedTextColor = Color.WHITE;
        mSelectedCircleBgColor = res.getColor(R.color.calendar_selected_circle_color);
        mTodayDateColor = res.getColor(R.color.accent_color);
        mNextMonthDateColor = res.getColor(R.color.next_month_color);
        mReminderIndicatorColor = res.getColor(R.color.white);

        CAL_FONT_SIZE = res.getDimensionPixelSize(R.dimen.mdtp_cal_font_size);
        MONTH_LABEL_TEXT_SIZE = res.getDimensionPixelSize(R.dimen.mdtp_month_label_size);
        //MONTH_HEADER_SIZE = res.getDimensionPixelOffset(com.wdullaer.materialdatetimepicker.R.dimen.mdtp_month_list_item_header_height);
        DAY_SELECTED_CIRCLE_SIZE = res.getDimensionPixelSize(R.dimen.mdtp_day_number_select_circle_radius);
        MAJOR_ROW_GAP = res.getDimensionPixelSize(R.dimen.major_row_gap);
        MINOR_ROW_GAP = res.getDimensionPixelSize(R.dimen.minor_row_gap);
        HEADING_ROW_GAP = res.getDimensionPixelSize(R.dimen.heading_row_gap);
        REMINDER_INDICATOR_CIRCLE_SIZE = res.getDimensionPixelSize(R.dimen.reminder_indicator_circle_radius);
        DAY_LABEL_GAP = res.getDimensionPixelSize(R.dimen.day_label_gap);
        mRowHeight = MINOR_ROW_GAP + CAL_FONT_SIZE;
        mGap = MINOR_ROW_GAP / 2;
        mHeaderHeight = HEADING_ROW_GAP + MONTH_LABEL_TEXT_SIZE;

        // Sets up any standard paints that will be used
        initView();
        setWillNotDraw(false);
    }

    /**
     * Start Drawing Calendar
     */
    @Override
    protected void onDraw(Canvas canvas) {
        setMonthParams();

        /** Do not display previous months*/
        if (currentMonth != mMonth || currentYear != mYear) {
            drawLeftArrow(canvas);
        }


        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
        drawPreviousMonthNums(canvas);
        drawRightArrow(canvas);
        if (mSelectedDay != -1) {
            Coordinate selectedCoordinate = getLocationFromDay(mSelectedDay);
            mSelectedCircleCanvasX = selectedCoordinate.getX();
            mSelectedCircleCanvasY = selectedCoordinate.getY();
            highlightSelectedDay(canvas);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        screenHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(screenWidth, (screenHeight) / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    public void setMonthParams() {
        Calendar month = Calendar.getInstance();
        month.set(Calendar.DAY_OF_MONTH, 1);
        month.set(Calendar.MONTH, mMonth);
        month.set(Calendar.YEAR, mYear);
        mStartingDayOfWeek = month.get(Calendar.DAY_OF_WEEK);
        mNumDaysInMonth = month.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * A wrapper to the MonthHeaderSize to allow override it in children
     */

    private String getMonthAndYearString() {
        getMonthName(mMonth);
        return (mMonthName + " " + mYear);
    }

    protected int findDayOffset() {
        return (mStartingDayOfWeek < mWeekStart ? (mStartingDayOfWeek + mNumDays) : mStartingDayOfWeek)
                - mWeekStart;
    }

    public void setSelectedDay(int day) {
        mSelectedDay = day;
    }

    protected void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mEdgePadding) / 2;
        int y = mHeaderHeight;
        canvas.drawText(getMonthAndYearString(), x, y, mMonthTitlePaint);
    }

    /*
    *Added by james_alcatraz on 31-12-2015
    */

    protected void drawMonthDayLabels(Canvas canvas) {
        int y = mHeaderHeight + mRowHeight + mDayLabelGap;
        int dayWidthHalf = (mWidth - mEdgePadding * 2) / (mNumDays * 2);
        for (int i = 0; i < mNumDays; i++) {
            int x = (2 * i + 1) * dayWidthHalf + mEdgePadding;
            int calendarDay = (i + mWeekStart) % mNumDays;
            mDayLabel.set(Calendar.DAY_OF_WEEK, calendarDay);
            Locale locale = Locale.getDefault();
            String localWeekDisplayName = mDayLabel.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale);
            String weekString = localWeekDisplayName.toUpperCase(locale).substring(0, 1);

            canvas.drawText(weekString, x, y, mMonthDayLabelPaint);
        }
    }

    /**
     * Draws the week and month day numbers for this week. Override this method
     * if you need different placement.
     *
     * @param canvas The canvas to draw on
     */
    protected void drawMonthNums(Canvas canvas) {
        int y = mHeaderHeight + 2 * mRowHeight;
        int x;
        mDayWidthHalf = (mWidth - mEdgePadding * 2) / (mNumDays * 2.0f);
        int j = findDayOffset();
        for (int dayNumber = 1; dayNumber <= mNumDaysInMonth; dayNumber++) {
            x = (int) ((2 * j + 1) * mDayWidthHalf + mEdgePadding);


            Coordinate coordinate = new Coordinate();
            coordinate.setX(x);
            coordinate.setY(y - 10);
            dayToCoordinate.put(dayNumber, coordinate);

            drawday(canvas, dayNumber, x, y);
            j++;
            if (j == mNumDays) {
                j = 0;
                y += mRowHeight;
            }
        }
        drawNextMonthNums(canvas, y);
    }

    protected void drawPreviousMonthNums(Canvas canvas) {

        int y = mHeaderHeight + 2 * mRowHeight;
        mDayWidthHalf = (mWidth - mEdgePadding * 2) / (mNumDays * 2.0f);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int dayNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = findDayOffset() - 1; j >= 0; j--) {
            final int x = (int) ((2 * j + 1) * mDayWidthHalf + mEdgePadding);
            canvas.drawText(String.format(Locale.UK, "%d", dayNumber), x, y, mNextMonthNumPaint);
            dayNumber--;
        }

    }

    protected void drawNextMonthNums(Canvas canvas, int y) {

        mDayWidthHalf = (mWidth - mEdgePadding * 2) / (mNumDays * 2.0f);
        int endDayNumber = (35 - (mNumDaysInMonth + findDayOffset()) + 7) % 7;
        int weekOffset = 7 - endDayNumber - 1;
        for (int dayNumber = 1; dayNumber <= endDayNumber; dayNumber++) {
            int x = (int) ((2 * (dayNumber + weekOffset) + 1) * mDayWidthHalf + mEdgePadding);
            canvas.drawText(String.format(Locale.UK, "%d", dayNumber), x, y, mNextMonthNumPaint);
        }

    }

    private void addDivider(Canvas canvas) {
        Drawable d = getResources().getDrawable(R.drawable.divider);
        assert d != null;
        d.setBounds(0, mHeaderHeight + mRowHeight, mWidth, mHeaderHeight + mRowHeight + 3);
        d.draw(canvas);
    }

    private boolean checkIfDateHasReminders(Date date, boolean lastDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        boolean isReminder = false;
        for (ReminderConfig reminderConfig : reminderDateList) {

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(reminderConfig.getReminderTime());

            if (cal1.get(Calendar.YEAR) <= cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR))
                continue;

            if (reminderConfig.getRecurringType() == context.getResources().getInteger(R.integer.RECURRING_MONTHLY)) {
                boolean finalRecurringFilter;
                if (lastDate) {         // for eg: if user sets 31st of January as a reminder-  it sets repetition for 28th feb, 29th feb, 30th april
                    finalRecurringFilter = (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) ||
                            cal1.get(Calendar.DAY_OF_MONTH) + 1 == cal2.get(Calendar.DAY_OF_MONTH) ||
                            cal1.get(Calendar.DAY_OF_MONTH) + 2 == cal2.get(Calendar.DAY_OF_MONTH) ||
                            cal1.get(Calendar.DAY_OF_MONTH) + 3 == cal2.get(Calendar.DAY_OF_MONTH));
                } else {
                    finalRecurringFilter = cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
                }
                if (finalRecurringFilter) {
                    isReminder = true;
                }
            } else if (reminderConfig.getRecurringType() == context.getResources().getInteger(R.integer.RECURRING_WEEKLY)) {
                if (cal2.get(Calendar.DAY_OF_WEEK) == cal1.get(Calendar.DAY_OF_WEEK))
                    isReminder = true;
            } else if (reminderConfig.getRecurringType() == context.getResources().getInteger(R.integer.RECURRING_DAILY)) {
                isReminder = true;
            } else {
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                    isReminder = true;
                }
            }

        }

        return isReminder;
    }

    public void drawday(Canvas canvas, int dayNumber, int x, int y) {
        Calendar c = Calendar.getInstance();
        c.set(mYear, mMonth, dayNumber, 0, 0);
        Date date = c.getTime();
        boolean lastDate = dayNumber == mNumDaysInMonth;
        if (dayNumber == currentDay && mMonth == currentMonth && mYear == currentYear) {
            canvas.drawText(String.format(Locale.UK, "%d", dayNumber), x, y, mTodayPaint);
        } else {
            canvas.drawText(String.format(Locale.UK, "%d", dayNumber), x, y, mMonthNumPaint);
        }
      /*  if (checkIfDateHasReminders(date, lastDate)) {
            canvas.drawCircle(x, y + 10, REMINDER_INDICATOR_CIRCLE_SIZE, mReminderIndicatorPaint);
        }*/
    }

    public void highlightSelectedDay(Canvas canvas) { //Used to draw circle on currently selected day. call this function only once in Drawcal!
        if (mSelectedDay != -1) {
            canvas.drawCircle(mSelectedCircleCanvasX, mSelectedCircleCanvasY, DAY_SELECTED_CIRCLE_SIZE,
                    mSelectedCirclePaint);
            drawday(canvas, mSelectedDay, (int) mSelectedCircleCanvasX, (int) mSelectedCircleCanvasY + 7);
        }

    }

    public Bitmap getScaledBitmap(Bitmap bm, float ratio) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(ratio, ratio);
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    }

    protected void drawLeftArrow(Canvas canvas) {
        Bitmap left = BitmapFactory.decodeResource(getResources(), R.drawable.calendar_arrow_left_white);
        Bitmap resizedLeft = getScaledBitmap(left, 0.4f);
        canvas.drawBitmap(resizedLeft, mDayWidthHalf * 1 / 2, 20, null);
    }

    protected void drawRightArrow(Canvas canvas) {
        Bitmap right = BitmapFactory.decodeResource(getResources(), R.drawable.calendar_arrow_right_white);
        Bitmap resizedRight =getScaledBitmap(right, 0.4f);
        canvas.drawBitmap(resizedRight, (mWidth - mDayWidthHalf * 3 / 2), 20, null);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mTouchX = event.getX();
                mTouchY = event.getY();
                if (mTouchY >= (mHeaderHeight + mRowHeight)) {
                    setSelectedDay(getDayFromLocation(mTouchX, mTouchY));
                } else if (mTouchY <= mHeaderHeight + 20) {
                    mSelectedDay = -1;
                    if (mMonth == currentMonth && mYear == currentYear) {
                        if (mTouchX >= mWidth - mDayWidthHalf * 2) {
                            mDateClickedListener.onMonthChangeClick();
                            setNextMonth();
                        }
                    } else {
                        if (mTouchX >= mWidth - mDayWidthHalf * 2) {
                            mDateClickedListener.onMonthChangeClick();
                            setNextMonth();
                        } else if (mTouchX <= mDayWidthHalf * 2) {
                            mDateClickedListener.onMonthChangeClick();
                            setPreviousMonth();
                        }
                    }
                }
                if (mSelectedDay != -1) {
                    invalidate();
                    boolean lastDate = mSelectedDay == mNumDaysInMonth;
                    mDateClickedListener.onDateClick(mSelectedDay, mMonth, mYear, lastDate, getMonthName(mMonth));
                }
                break;
            case MotionEvent.ACTION_UP:

                mTouchX2 = event.getX();
                float deltaX = mTouchX2 - mTouchX;

                if (Math.abs(deltaX) > SWIPE_THRESHOLD) {
                    // Left to Right swipe action
                    if (mTouchX2 > mTouchX) {
                        if (mMonth != currentMonth || mYear != currentYear) {
                            setPreviousMonth();
                            mDateClickedListener.onMonthChangeClick();
                            boolean lastDate = mSelectedDay == mNumDaysInMonth;
                            mDateClickedListener.onDateClick(mSelectedDay, mMonth, mYear, lastDate, getMonthName(mMonth));
                        }
                    }

                    // Right to left swipe action
                    else {
                        setNextMonth();
                        mDateClickedListener.onMonthChangeClick();
                        boolean lastDate = mSelectedDay == mNumDaysInMonth;
                        mDateClickedListener.onDateClick(mSelectedDay, mMonth, mYear, lastDate, getMonthName(mMonth));
                    }

                } else {
                    // consider as something else - a screen tap for example
                }
                break;
        }

        return true;
    }

    public void setDateClickListener(DateClickedListener onDateClickListener) {
        mDateClickedListener = onDateClickListener;
    }

    protected void setNextMonth() {
        if (mMonth < 11) {
            mMonth += 1;
            mSelectedDay = 1;
            invalidate();
        } else {
            mMonth = 0;
            mYear += 1;
            mSelectedDay = 1;
            invalidate();
        }
    }

    protected void setPreviousMonth() {
        if (mMonth > 0) {
            mMonth -= 1;
            mSelectedDay = 1;
            invalidate();
        } else {
            mMonth = 11;
            mYear -= 1;
            mSelectedDay = 1;
            invalidate();
        }
        if (mMonth == currentMonth && mYear == currentYear) {
            mSelectedDay = currentDay;
        }
    }

    public int getDayFromLocation(float x, float y) {
        final int day = getInternalDayFromLocation(x, y);
        if (day < 1 || day > mNumDaysInMonth) {
            return -1;
        }
        return day;
    }

    protected int getInternalDayFromLocation(float x, float y) {
        int dayStart = mEdgePadding;
        if (x < dayStart || x > mWidth - mEdgePadding) {
            return -1;
        }
        // Selection is (x - start) / (pixels/day) == (x -s) * day / pixels
        int row = (int) ((y - mHeaderHeight - mRowHeight - mGap / 2) / mRowHeight);
        int column = (int) ((x - dayStart) * mNumDays / (mWidth - dayStart - mEdgePadding));

        int day = column - findDayOffset() + 8;
        day += ((row - 1) * mNumDays);
        setDayCanvasCoordinates(row, column);
        return day;
    }

    public void setDayCanvasCoordinates(int r, int c) {
        mSelectedCircleCanvasX = mEdgePadding + (c * 2 + 1) * mDayWidthHalf;
        mSelectedCircleCanvasY = mHeaderHeight + (r + 2) * mRowHeight - CAL_FONT_SIZE / 3;

        mSelectedNumCanvasX = mEdgePadding + (c * 2 + 1) * mDayWidthHalf;
        mSelectedNumCanvasY = mHeaderHeight + (r + 2) * mRowHeight;
    }

    /**
     * Sets up the text and style properties for painting. Override this if you
     * want to use a different paint.
     */
    protected void initView() {
        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MONTH_LABEL_TEXT_SIZE);
        mMonthTitlePaint.setColor(mDayTextColor);
        mMonthTitlePaint.setTextAlign(Paint.Align.CENTER);
        mMonthTitlePaint.setStyle(Paint.Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(getContext().getResources().getColor(R.color.calendar_selected_circle_color));
        mSelectedCirclePaint.setTextAlign(Paint.Align.CENTER);
        mSelectedCirclePaint.setStyle(Paint.Style.FILL);
        mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(CAL_FONT_SIZE);
        mMonthDayLabelPaint.setColor(mMonthDayTextColor);
        mMonthDayLabelPaint.setStyle(Paint.Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Paint.Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(CAL_FONT_SIZE);
        mMonthNumPaint.setColor(mDayTextColor);
        mMonthNumPaint.setStyle(Paint.Style.FILL);
        mMonthNumPaint.setTextAlign(Paint.Align.CENTER);
        mMonthNumPaint.setFakeBoldText(false);

        mSelectedNumPaint = new Paint();
        mSelectedNumPaint.setAntiAlias(true);
        mSelectedNumPaint.setTextSize(CAL_FONT_SIZE);
        mSelectedNumPaint.setColor(mDaySelectedTextColor);
        mSelectedNumPaint.setStyle(Paint.Style.FILL);
        mSelectedNumPaint.setTextAlign(Paint.Align.CENTER);
        mSelectedNumPaint.setFakeBoldText(false);

        mTodayPaint = new Paint();
        mTodayPaint.setAntiAlias(true);
        mTodayPaint.setTextSize(CAL_FONT_SIZE);
        mTodayPaint.setColor(mTodayDateColor);
        mTodayPaint.setStyle(Paint.Style.FILL);
        mTodayPaint.setTextAlign(Paint.Align.CENTER);
        mTodayPaint.setFakeBoldText(true);

        mReminderPaint = new Paint();
        mReminderPaint.setAntiAlias(true);
        mReminderPaint.setTextSize(CAL_FONT_SIZE);
        mReminderPaint.setColor(getContext().getResources().getColor(R.color.calendar_reminder));
        mReminderPaint.setStyle(Paint.Style.FILL);
        mReminderPaint.setTextAlign(Paint.Align.CENTER);
        mReminderPaint.setFakeBoldText(true);

        mReminderIndicatorPaint = new Paint();
        mReminderIndicatorPaint.setColor(mReminderIndicatorColor);

        mNextMonthNumPaint = new Paint();
        mNextMonthNumPaint.setAntiAlias(true);
        mNextMonthNumPaint.setTextSize(CAL_FONT_SIZE);
        mNextMonthNumPaint.setColor(mNextMonthDateColor);
        mNextMonthNumPaint.setStyle(Paint.Style.FILL);
        mNextMonthNumPaint.setTextAlign(Paint.Align.CENTER);
        mNextMonthNumPaint.setFakeBoldText(false);
    }

    protected String getMonthName(int month) {
        if (month == 0) mMonthName = "January";
        else if (month == 1) mMonthName = "February";
        else if (month == 2) mMonthName = "March";
        else if (month == 3) mMonthName = "April";
        else if (month == 4) mMonthName = "May";
        else if (month == 5) mMonthName = "June";
        else if (month == 6) mMonthName = "July";
        else if (month == 7) mMonthName = "August";
        else if (month == 8) mMonthName = "September";
        else if (month == 9) mMonthName = "October";
        else if (month == 10) mMonthName = "November";
        else mMonthName = "December";
        return mMonthName;
    }

    public void setDateList(List<ReminderConfig> dateList) {
        if(reminderDateList!= null){
        reminderDateList = dateList;
        List<Integer> remDates = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++)
            remDates.add(dateList.get(i).getReminderTime().getDate());
    }}

    public void setRemTextList(List<String> textList) {
        List<String> remText = new ArrayList<>();
        for (int i = 0; i < textList.size(); i++)
            remText.add(textList.get(i));
    }

    protected void getItemBounds(int day, Rect rect) {
        final int offsetX = mEdgePadding;
        final int offsetY = mHeaderHeight;
        final int cellHeight = mRowHeight;
        final int cellWidth = ((mWidth - (2 * mEdgePadding)) / mNumDays);
        final int index = ((day - 1) + findDayOffset());
        final int row = (index / mNumDays) + 1;
        final int column = (index % mNumDays);
        final int x = (offsetX + (column * cellWidth));
        final int y = (offsetY + (row * cellHeight));

        rect.set(x, y, (x + cellWidth), (y + cellHeight));
    }

    public Coordinate getLocationFromDay(int day) {
        return dayToCoordinate.get(day);
    }

    /**
     * A convenience class to represent a specific date.
     */
    public static class CalendarDay {
        int year;
        int month;
        int day;
        private Calendar calendar;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public void set(CalendarDay date) {
            year = date.year;
            month = date.month;
            day = date.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        public int getYear() {
            return year;
        }

        public int getMonth() {
            return month;
        }

        public int getDay() {
            return day;
        }
    }

    public class Coordinate {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
