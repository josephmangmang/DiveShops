package com.divetym.dive.ui.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.divetym.dive.R;

/**
 * Created by kali_root on 4/5/2017.
 */

public class ToastAlert extends Toast {
    private TextView tvMessage;
    private LinearLayout root;
    private Context mContext;

    public ToastAlert(Context context) {
        super(context);
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        tvMessage = (TextView) view.findViewById(R.id.message);
        root = (LinearLayout) view.findViewById(R.id.background);
        setGravity(Gravity.BOTTOM, 0, 50);
        setDuration(LENGTH_SHORT);
        setView(view);
    }

    public ToastAlert setBackgroundColor(@ColorInt int color) {
        root.setBackgroundColor(color);
        return this;
    }

    public ToastAlert setMessage(@StringRes int message) {
        setMessage(mContext.getString(message));
        return this;
    }

    public ToastAlert setMessage(String message) {
        tvMessage.setText(message);
        return this;
    }

}
