package com.divetym.dive.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 4/15/2017.
 */

public abstract class DetailsActivity extends AuthenticatedActivity {
    public static final String EXTRA_DATA = "com.divetym.dive.EXTRA_DATA";
    private static final String TAG = DetailsActivity.class.getSimpleName();
    @BindView(R.id.button_book_now)
    FloatingActionButton fabBuyNow;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.text_subtitle)
    RobotoTextView mToolbarSubtitle;
    @BindView(R.id.text_body)
    protected RobotoTextView tvBody;
    @BindView(R.id.image_collapsing_toolbar_background)
    protected ImageView ivToolbarBackground;


    @OnClick(R.id.button_book_now)
    public void onBookNowClick() {
        Log.d(TAG, "onBookNowClick");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        showBackButton(true);
        ButterKnife.bind(this);
        setData();
    }

    protected abstract void setData();

    public void setToolbarTitle(String title) {
        mCollapsingLayout.setTitle(title);
    }

    public void setToolbarSubtitle(String subtitle) {
        mToolbarSubtitle.setText(subtitle);
    }
}
