package com.divetym.dive.ui.activities.diver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.LocationAddress;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverTripFragment;
import com.divetym.dive.ui.view.TripFilterLayout;
import com.divetym.dive.utils.DateUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.fragments.diver.DailyTripSearchFragment.*;


/**
 * Created by kali_root on 6/10/2017.
 */

public class DiverTripActivity extends DiveTymActivity {
    @BindView(R.id.trip_filter_layout)
    TripFilterLayout mTripFilterLayout;
    DiverTripFragment fragment;

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DiverTripActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private TripFilterLayout.OnFilterChangeListener onFilterChangeListener = (startDate, endDate, locationAddress, diveSite) -> {
        fragment.refreshTripList(
                DateUtils.formatServerDate(startDate),
                DateUtils.formatServerDate(endDate),
                locationAddress != null ? locationAddress.getLatLng() : new LatLng(0, 0),
                diveSite != null ? diveSite.getDiveSiteId() : -1);
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_trip_activity);
        showBackButton(true);
        ButterKnife.bind(this);
        fragment = initFragment(R.id.content, new DiverTripFragment());

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
        mTripFilterLayout.setOnFilterChangeListener(onFilterChangeListener);
        mTripFilterLayout.setStartDate(start);
        mTripFilterLayout.setEndDate(end);
        mTripFilterLayout.setDiveSite(diveSite);
        mTripFilterLayout.setLocation(location);
        mTripFilterLayout.notifyFilterChanged();
    }


}
