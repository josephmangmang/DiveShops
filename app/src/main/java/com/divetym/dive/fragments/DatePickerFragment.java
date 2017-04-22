package com.divetym.dive.fragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by kali_root on 4/22/2017.
 */

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {
    public static final String TAG = DatePickerFragment.class.getSimpleName();
    private Calendar calendar;
    private int id;


    private OnDateRangeChangedListener onDateRangeChangedListener;

    public interface OnDateRangeChangedListener {
        void onDateRangeChanged(int id, Calendar calendar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, y, m, d);
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOnDateRangeChangedListener(OnDateRangeChangedListener onDateRangeChangedListener) {
        this.onDateRangeChangedListener = onDateRangeChangedListener;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        if (onDateRangeChangedListener != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(y, m, d);
            onDateRangeChangedListener.onDateRangeChanged(id, calendar);
        }
    }

}
