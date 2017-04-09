package com.divetym.dive.ui.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.common.SessionManager;
import com.divetym.dive.ui.fragments.DiveShopFragment;
import com.divetym.dive.ui.view.ToastAlert;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AuthenticatedActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        ButterKnife.bind(this);
        loadScreen();
    }

    public CollapsingToolbarLayout getCollapsingToolbarLayout() {
        return mCollapsingToolbarLayout;
    }

    private void loadScreen() {
        switch (SessionManager.getInstance(this).getAccountType()) {
            case Diver:
                // TODO: 4/6/2017 implement diver profile screen
                Log.i(TAG, "loading Diver's screen..");
                break;
            case Dive_Shop:
                Log.i(TAG, "loading Dive_Shop's screen..");
                initFragment(R.id.content, new DiveShopFragment());
                break;
            default:
                new ToastAlert(this)
                        .setMessage(R.string.error_account_not_valid)
                        .show();
        }
    }
}
