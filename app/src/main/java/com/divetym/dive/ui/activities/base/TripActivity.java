package com.divetym.dive.ui.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.LocationAddress;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.ui.fragments.diver.DiverTripFragment;
import com.divetym.dive.ui.view.TripFilterLayout;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static com.divetym.dive.ui.activities.diver.DailyTripSearchActivity.BUNDLE_DIVE_SITE;
import static com.divetym.dive.ui.activities.diver.DailyTripSearchActivity.BUNDLE_END_DATE;
import static com.divetym.dive.ui.activities.diver.DailyTripSearchActivity.BUNDLE_LOCATION;
import static com.divetym.dive.ui.activities.diver.DailyTripSearchActivity.BUNDLE_START_DATE;


/**
 * Created by kali_root on 6/10/2017.
 */

public abstract class TripActivity<Frag extends DiveTymListFragment> extends DiveTymActivity {
    @BindView(R.id.trip_filter_layout)
    protected TripFilterLayout mTripFilterLayout;
    protected Frag mFragment;
    protected BottomSheetBehavior<TripFilterLayout> mBottomSheet;

    private TripFilterLayout.OnFilterChangeListener onFilterChangeListener = (startDate, endDate, locationAddress, diveSite) -> {
        filterChange(startDate, endDate, locationAddress, diveSite);
    };

    protected abstract void filterChange(Date startDate, Date endDate, LocationAddress locationAddress, DiveSite diveSite);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_trip_activity);
        showBackButton(true);
        ButterKnife.bind(this);
        mFragment = getFragment();

        Intent intent = getIntent();
        DiveSite diveSite = intent.getParcelableExtra(BUNDLE_DIVE_SITE);
        LocationAddress location = intent.getParcelableExtra(BUNDLE_LOCATION);

        long s = intent.getLongExtra(BUNDLE_START_DATE, 0);
        long e = (intent.getLongExtra(BUNDLE_END_DATE, 0));
        Date start = new Date(s);
        Date end = new Date(e);
        if (s == 0 || e == 0) {
            start = mTripFilterLayout.getStartDate();
            end = mTripFilterLayout.getEndDate();
        }

        mBottomSheet = BottomSheetBehavior.from(mTripFilterLayout);
        mBottomSheet.setPeekHeight(120);
        mTripFilterLayout.setOnFilterChangeListener(onFilterChangeListener);
        mTripFilterLayout.setStartDate(start);
        mTripFilterLayout.setEndDate(end);
        mTripFilterLayout.setDiveSite(diveSite);
        mTripFilterLayout.setLocation(location);
        mTripFilterLayout.notifyFilterChanged();
        mTripFilterLayout.setFilterButtonClickListener(view -> {
            switch (mBottomSheet.getState()) {
                case STATE_EXPANDED:
                    mBottomSheet.setState(STATE_COLLAPSED);
                    break;
                case STATE_COLLAPSED:
                    mBottomSheet.setState(STATE_EXPANDED);
            }
        });
    }

    protected abstract Frag getFragment();


}
