package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.common.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.divetym.dive.ui.view.DateRangeLayout.DateRange.StartDate;

/**
 * Created by kali_root on 4/22/2017.
 */

public class DateRangeLayout extends LinearLayout implements DatePickerFragment.OnDateChangeListener {
    public static final String TAG = DateRangeLayout.class.getSimpleName();
    private RobotoTextView dateStartTextView;
    private RobotoTextView dateEndTextView;
    private DiveTymActivity mContext;
    private SimpleDateFormat mDateFormat;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private OnDateRangeChangeListener mOnDateRangeChangeListener;

    public enum DateRange {
        StartDate, EndDate
    }

    public DateRangeLayout(Context context) {
        super(context);
        initialize(context);
    }

    public DateRangeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public DateRangeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.view_date_range, this);
        if(context instanceof ContextWrapper) {
            mContext = (DiveTymActivity) ((ContextWrapper)context).getBaseContext();
        }
        dateStartTextView = (RobotoTextView) findViewById(R.id.text_date_start);
        dateEndTextView = (RobotoTextView) findViewById(R.id.text_date_end);

        mDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DAY_OF_MONTH, 30);

        dateStartTextView.setText(mDateFormat.format(startCalendar.getTime()));
        dateEndTextView.setText(mDateFormat.format(endCalendar.getTime()));

        dateStartTextView.setOnClickListener(view -> {
            if (mContext == null) {
                return;
            }
            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.setDateRange(StartDate);
            datePicker.setCalendar(startCalendar);
            datePicker.setOnDateChangeListener(DateRangeLayout.this);
            datePicker.show(mContext.getFragmentManager(), "startdatepicker");
        });
        dateEndTextView.setOnClickListener(view -> {
            if (mContext == null) {
                return;
            }
            DatePickerFragment datePicker = new DatePickerFragment();
            datePicker.setDateRange(DateRange.EndDate);
            datePicker.setCalendar(endCalendar);
            datePicker.setOnDateChangeListener(DateRangeLayout.this);
            datePicker.show(mContext.getFragmentManager(), "enddatepicker");
        });
    }

    public void setContext(DiveTymActivity context) {
        mContext = context;
    }

    public void setOnDateRangeChangeListener(OnDateRangeChangeListener onDateRangeChangeListener) {
        mOnDateRangeChangeListener = onDateRangeChangeListener;
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
                dateStartTextView.setText(mDateFormat.format(calendar.getTime()));
                break;
            case EndDate:
                endCalendar = calendar;
                dateEndTextView.setText(mDateFormat.format(calendar.getTime()));
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
