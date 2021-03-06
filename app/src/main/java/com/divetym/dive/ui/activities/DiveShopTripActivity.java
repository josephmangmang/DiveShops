package com.divetym.dive.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;

import com.divetym.dive.R;
import com.divetym.dive.event.DailyTripEvent;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.fragments.DiveShopTripFragment;
import com.divetym.dive.ui.view.TripFilterLayout;
import com.divetym.dive.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 4/21/2017.
 */

public class DiveShopTripActivity extends AuthenticatedActivity {
    private static final String TAG = DiveShopTripActivity.class.getSimpleName();
    public static final int REQUEST_REFRESH = 1;
    @BindView(R.id.trip_filter_layout)
    TripFilterLayout mTripFilterLayout;
    private DiveShopTripFragment mFragment;

    private TripFilterLayout.OnFilterChangeListener onFilterChangeListener = (startDate, endDate, locationAddress, diveSite) -> {
        mFragment.refreshTripList(
                DateUtils.formatServerDate(startDate),
                DateUtils.formatServerDate(endDate),
                diveSite != null ? diveSite.getDiveSiteId() : -1);
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_trips);
        ButterKnife.bind(this);
        showBackButton(true);

        mTripFilterLayout.setOnFilterChangeListener(onFilterChangeListener);
        mTripFilterLayout.showLocationFilter(false);
        mFragment = initFragment(R.id.content, new DiveShopTripFragment());
        mFragment.refreshTripList(
                DateUtils.formatServerDate(mTripFilterLayout.getStartDate()),
                DateUtils.formatServerDate(mTripFilterLayout.getEndDate()),
                -1);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (mFragment != null) {
            mFragment.inflateToolbar(menu, getMenuInflater());
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDailyTripChanged(DailyTripEvent event) {
        Log.d(TAG, "onDailyTripChanged: " + event);
        if (mFragment != null) {
            mFragment.refresh();
        }
        EventBus.getDefault().removeStickyEvent(event);
    }
}
