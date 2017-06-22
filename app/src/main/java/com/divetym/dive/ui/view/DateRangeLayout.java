package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.fragments.common.DatePickerFragment;
import com.divetym.dive.utils.DateUtils;

import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.divetym.dive.ui.view.DateRangeLayout.DateRange.StartDate;

/**
 * Created by kali_root on 4/22/2017.
 */

public class DateRangeLayout extends LinearLayout implements DatePickerFragment.OnDateChangeListener {
    public static final String TAG = DateRangeLayout.class.getSimpleName();
    @BindView(R.id.text_label_start)
    RobotoTextView startLabelText;
    @BindView(R.id.text_date_start_day)
    RobotoTextView startDayText;
    @BindView(R.id.text_date_start_month)
    RobotoTextView startMonthText;
    @BindView(R.id.text_date_start_week)
    RobotoTextView startWeekText;

    @BindView(R.id.text_label_end)
    RobotoTextView endLabelText;
    @BindView(R.id.text_date_end_day)
    RobotoTextView endDayText;
    @BindView(R.id.text_date_end_month)
    RobotoTextView endMonthText;
    @BindView(R.id.text_date_end_week)
    RobotoTextView endWeekText;

    private DiveTymActivity mContext;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private OnDateRangeChangeListener mOnDateRangeChangeListener;

    public enum DateRange {
        StartDate, EndDate
    }

    @OnClick(R.id.layout_start_date)
    public void onStartDateClick() {
        if (mContext == null) {
            return;
        }
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setDateRange(StartDate);
        datePicker.setCalendar(startCalendar);
        datePicker.setOnDateChangeListener(DateRangeLayout.this);
        datePicker.show(mContext.getFragmentManager(), "startdatepicker");
    }

    @OnClick(R.id.layout_end_date)
    public void onEndDateClick() {
        if (mContext == null) {
            return;
        }
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.setDateRange(DateRange.EndDate);
        datePicker.setCalendar(endCalendar);
        datePicker.setOnDateChangeListener(DateRangeLayout.this);
        datePicker.show(mContext.getFragmentManager(), "enddatepicker");
    }

    public DateRangeLayout(Context context) {
        super(context);
        initialize(context, null);
    }

    public DateRangeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public DateRangeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_date_range, this);
        if (context instanceof DiveTymActivity) {
            mContext = (DiveTymActivity) context;
        } else if (context instanceof ContextWrapper) {
            try {
                Context baseContext = ((ContextWrapper) context).getBaseContext();
                mContext = (DiveTymActivity) baseContext;
            } catch (ClassCastException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        ButterKnife.bind(this);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateRangeLayout);
            if (a.hasValue(R.styleable.DateRangeLayout_label_color)) {
                int color = a.getColor(R.styleable.DateRangeLayout_label_color, ContextCompat.getColor(context, R.color.grey_700));
                startLabelText.setTextColor(color);
                endLabelText.setTextColor(color);
            }
            if (a.hasValue(R.styleable.DateRangeLayout_day_color)) {
                int color = a.getColor(R.styleable.DateRangeLayout_day_color, ContextCompat.getColor(context, R.color.colorAccent));
                startDayText.setTextColor(color);
                endDayText.setTextColor(color);
            }
            if (a.hasValue(R.styleable.DateRangeLayout_month_color)) {
                int color = a.getColor(R.styleable.DateRangeLayout_month_color, ContextCompat.getColor(context, R.color.grey_800));
                endMonthText.setTextColor(color);
                startMonthText.setTextColor(color);
            }
            if (a.hasValue(R.styleable.DateRangeLayout_week_color)) {
                int color = a.getColor(R.styleable.DateRangeLayout_week_color, ContextCompat.getColor(context, R.color.grey_600));
                startWeekText.setTextColor(color);
                endWeekText.setTextColor(color);
            }

        }

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DAY_OF_MONTH, 30);

        updateStartDate();
        updateEndDate();
    }

    public void setContext(DiveTymActivity context) {
        this.mContext = context;
    }

    private void updateEndDate() {
        endDayText.setText(DateUtils.getDayInMonth(endCalendar.getTime().getTime(), Locale.getDefault()));
        endWeekText.setText(DateUtils.getDayNameInWeek(endCalendar.getTimeInMillis(), Locale.getDefault()));
        endMonthText.setText(
                DateUtils.getMonthInYearWithYear(
                        endCalendar.getTimeInMillis(),
                        Locale.getDefault(),
                        !DateUtils.isThisYear(endCalendar)));
    }

    private void updateStartDate() {
        startDayText.setText(DateUtils.getDayInMonth(startCalendar.getTimeInMillis(), Locale.getDefault()));
        startWeekText.setText(DateUtils.getDayNameInWeek(startCalendar.getTimeInMillis(), Locale.getDefault()));
        startMonthText.setText(
                DateUtils.getMonthInYearWithYear(
                        startCalendar.getTimeInMillis(),
                        Locale.getDefault(),
                        !DateUtils.isThisYear(startCalendar)));

    }


    public void setOnDateRangeChangeListener(OnDateRangeChangeListener onDateRangeChangeListener) {
        mOnDateRangeChangeListener = onDateRangeChangeListener;
    }

    public void setStartCalendar(Calendar startCalendar) {
        this.startCalendar = startCalendar;
        updateStartDate();
    }

    public void setEndCalendar(Calendar endCalendar) {
        this.endCalendar = endCalendar;
        updateEndDate();
    }

    public Calendar getStartCalendar() {
        return startCalendar;
    }

    public Calendar getEndCalendar() {
        return endCalendar;
    }

    @Override
    public void onDateChanged(DateRange dateRange, Calendar calendar) {
        switch (dateRange) {
            case StartDate:
                startCalendar = calendar;
                updateStartDate();
                break;
            case EndDate:
                endCalendar = calendar;
                updateEndDate();
                break;
        }
        if (mOnDateRangeChangeListener != null) {
            mOnDateRangeChangeListener.onDateRangeChanged(startCalendar, endCalendar);
        }
    }

    public interface OnDateRangeChangeListener {
        void onDateRangeChanged(Calendar startDate, Calendar endDate);
    }
}
