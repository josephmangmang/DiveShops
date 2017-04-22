package com.divetym.dive.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.fragments.DatePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kali_root on 4/22/2017.
 */

public class DateRangeLayout extends LinearLayout implements DatePickerFragment.OnDateRangeChangedListener {
    public static final String TAG = DateRangeLayout.class.getSimpleName();
    public static final int DATE_START = 10;
    public static final int DATE_END = 11;
    private RobotoTextView tvDateStart;
    private RobotoTextView tvDateEnd;
    private DiveTymActivity mContext;
    private SimpleDateFormat mDateFormat;
    private Calendar startCalendar;
    private Calendar endCalendar;

    public DateRangeLayout(Context context) {
        super(context);
        initialize();
    }

    public DateRangeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public DateRangeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_date_range, this, true);
        tvDateStart = (RobotoTextView) findViewById(R.id.text_date_start);
        tvDateEnd = (RobotoTextView) findViewById(R.id.text_date_end);

        mDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.DAY_OF_MONTH, 30);

        tvDateStart.setText(mDateFormat.format(startCalendar.getTime()));
        tvDateEnd.setText(mDateFormat.format(endCalendar.getTime()));

        tvDateStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext == null) {
                    return;
                }
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setId(DATE_START);
                datePicker.setCalendar(startCalendar);
                datePicker.setOnDateRangeChangedListener(DateRangeLayout.this);
                datePicker.show(mContext.getFragmentManager(), "startdatepicker");
            }
        });
        tvDateEnd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext == null) {
                    return;
                }
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.setId(DATE_END);
                datePicker.setCalendar(endCalendar);
                datePicker.setOnDateRangeChangedListener(DateRangeLayout.this);
                datePicker.show(mContext.getFragmentManager(), "enddatepicker");
            }
        });
    }

    public void setContext(DiveTymActivity context) {
        mContext = context;
    }


    @Override
    public void onDateRangeChanged(int id, Calendar calendar) {
        if (id == DATE_START) {
            startCalendar = calendar;
            tvDateStart.setText(mDateFormat.format(calendar.getTime()));
        } else if (id == DATE_END) {
            endCalendar = calendar;
            tvDateEnd.setText(mDateFormat.format(calendar.getTime()));
        }
    }
}
