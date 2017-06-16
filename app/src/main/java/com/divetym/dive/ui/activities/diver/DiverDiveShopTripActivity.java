package com.divetym.dive.ui.activities.diver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.DiveShopTripFragment;
import com.divetym.dive.ui.fragments.diver.DiverDiveShopTripFragment;
import com.divetym.dive.ui.view.TripFilterLayout;
import com.divetym.dive.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.diver.DiverDiveShopActivity.EXTRA_DIVE_SHOP_NAME;
import static com.divetym.dive.ui.activities.diver.DiverDiveShopActivity.EXTRA_DIVE_SHOP_UID;

/**
 * Created by kali_root on 4/21/2017.
 */

public class DiverDiveShopTripActivity extends DiveTymActivity {
    private static final String TAG = DiverDiveShopTripActivity.class.getSimpleName();
    public static final int REQUEST_REFRESH = 1;
    @BindView(R.id.trip_filter_layout)
    TripFilterLayout mTripFilterLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    private DiverDiveShopTripFragment mFragment;


    public static void launch(DiveTymActivity context, String diveShopUid, String diveShopName) {
        Intent intent = new Intent(context, DiverDiveShopTripActivity.class);
        intent.putExtra(EXTRA_DIVE_SHOP_UID, diveShopUid);
        intent.putExtra(EXTRA_DIVE_SHOP_NAME, diveShopName);
        context.startActivity(intent);
    }

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
        String shopName = getIntent().getStringExtra(EXTRA_DIVE_SHOP_NAME);
        if (shopName != null) {
            setTitle(shopName);
            mToolbarLayout.setTitle(shopName);
        }
        mTripFilterLayout.setOnFilterChangeListener(onFilterChangeListener);
        mTripFilterLayout.showLocationFilter(false);
        mFragment = initFragment(
                R.id.content,
                DiverDiveShopTripFragment.newInstance(getIntent().getStringExtra(EXTRA_DIVE_SHOP_UID)
                ));
        mFragment.refreshTripList(
                DateUtils.formatServerDate(mTripFilterLayout.getStartDate()),
                DateUtils.formatServerDate(mTripFilterLayout.getEndDate()),
                -1);
    }

}
