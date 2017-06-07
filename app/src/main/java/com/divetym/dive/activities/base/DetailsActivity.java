package com.divetym.dive.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.models.User;
import com.divetym.dive.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 4/15/2017.
 */

public abstract class DetailsActivity extends AuthenticatedActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();
    public static final String EXTRA_DATA = "com.divetym.dive.EXTRA_DATA";
    protected static final int REQUEST_UPDATE_DETAILS = 1;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.text_subtitle)
    RobotoTextView mToolbarSubtitle;
    @BindView(R.id.text_body)
    protected RobotoTextView mBodyText;
    @BindView(R.id.image_collapsing_toolbar_background)
    protected ImageView mToolbarBackgroundImage;
    private boolean diveshop;

    @OnClick(R.id.fab)
    public void onFabClicked() {
        Log.d(TAG, "onFabClicked");
        onFabClicked(diveshop);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        showBackButton(true);
        ButterKnife.bind(this);
        diveshop = SessionManager.getInstance(this).getAccountType() == User.AccountType.Dive_Shop;
        if (diveshop) {
            mFab.setImageResource(R.drawable.ic_edit);
        }
        setData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_UPDATE_DETAILS) {
            // update details
            if (data != null) updateDetails(data);
        }
    }

    private void updateDetails(Intent data) {
        setIntent(data);
        setData();
    }

    protected abstract void onFabClicked(boolean isDiveShop);

    protected abstract void setData();

    public void setToolbarTitle(String title) {
        mCollapsingLayout.setTitle(title);
    }

    public void setToolbarSubtitle(String subtitle) {
        mToolbarSubtitle.setText(subtitle);
    }
}
