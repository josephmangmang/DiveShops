package com.divetym.dive.ui.activities.diver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.LocationAddress;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.activities.base.TripActivity;
import com.divetym.dive.ui.fragments.diver.DiverDiveShopTripFragment;
import com.divetym.dive.utils.DateUtils;

import java.util.Date;

import static com.divetym.dive.ui.activities.diver.DiverDiveShopActivity.EXTRA_DIVE_SHOP_NAME;
import static com.divetym.dive.ui.activities.diver.DiverDiveShopActivity.EXTRA_DIVE_SHOP_UID;

/**
 * Created by kali_root on 4/21/2017.
 */

public class DiverDiveShopTripActivity extends TripActivity<DiverDiveShopTripFragment> {
    private static final String TAG = DiverDiveShopTripActivity.class.getSimpleName();


    public static void launch(DiveTymActivity context, String diveShopUid, String diveShopName) {
        Intent intent = new Intent(context, DiverDiveShopTripActivity.class);
        intent.putExtra(EXTRA_DIVE_SHOP_UID, diveShopUid);
        intent.putExtra(EXTRA_DIVE_SHOP_NAME, diveShopName);
        context.startActivity(intent);
    }

    @Override
    protected void filterChange(Date startDate, Date endDate, LocationAddress locationAddress, DiveSite diveSite) {
        mFragment.refreshTripList(
                DateUtils.formatServerDate(startDate),
                DateUtils.formatServerDate(endDate),
                diveSite != null ? diveSite.getDiveSiteId() : -1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String shopName = getIntent().getStringExtra(EXTRA_DIVE_SHOP_NAME);
        if (shopName != null) {
            setTitle(shopName);
        }
        mTripFilterLayout.showLocationFilter(false);
    }

    @Override
    protected DiverDiveShopTripFragment getFragment() {
        return initFragment(
                R.id.content,
                DiverDiveShopTripFragment.newInstance(getIntent().getStringExtra(EXTRA_DIVE_SHOP_UID)
                ));
    }

}
