package com.divetym.dive.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.divetym.dive.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 6/15/2017.
 */

public class InfoLayout extends CardView {
    @BindView(R.id.text_info_title)
    RobotoTextView infoTitle;
    @BindView(R.id.text_info_body)
    RobotoTextView infoBody;
    @BindView(R.id.button_show_more)
    Button showMoreButton;

    public InfoLayout(Context context) {
        super(context);
        init(context, null);
    }

    public InfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public InfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_info, this);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfoLayout);
            if (a.hasValue(R.styleable.InfoLayout_title)) {
                String title = a.getString(R.styleable.InfoLayout_title);
                infoTitle.setText(title);
            }
            a.recycle();
        }
    }

    @OnClick(R.id.button_show_more)
    public void onShowMoreClick(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
        infoBody.setMaxLines(Integer.MAX_VALUE);
        infoBody.setEllipsize(null);
    }

    public void setInfoTitle(String title) {
        infoTitle.setText(title);
    }

    public void setInfoBody(String body) {
        infoBody.setText(body);
    }

    public void setExpand(boolean expand) {
        infoBody.setMaxLines(expand ? Integer.MAX_VALUE : 2);
        infoBody.setEllipsize(expand ? null : TextUtils.TruncateAt.MARQUEE);
        showMoreButton.setVisibility(expand ? GONE : VISIBLE);
    }
}
