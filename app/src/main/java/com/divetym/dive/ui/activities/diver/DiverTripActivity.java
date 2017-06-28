package com.divetym.dive.ui.activities.diver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.divetym.dive.R;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.LocationAddress;
import com.divetym.dive.ui.activities.base.TripActivity;
import com.divetym.dive.ui.fragments.diver.DiverTripFragment;
import com.divetym.dive.utils.DateUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;


/**
 * Created by kali_root on 6/10/2017.
 */

public class DiverTripActivity extends TripActivity<DiverTripFragment> {

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, DiverTripActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void filterChange(Date startDate, Date endDate, LocationAddress locationAddress, DiveSite diveSite) {
        mFragment.refreshTripList(
                DateUtils.formatServerDate(startDate),
                DateUtils.formatServerDate(endDate),
                locationAddress != null ? locationAddress.getLatLng() : new LatLng(0, 0),
                diveSite != null ? diveSite.getDiveSiteId() : -1);
    }

    @Override
    protected DiverTripFragment getFragment() {
        return initFragment(R.id.content, new DiverTripFragment());
    }
}
