package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.fragments.TripDetailsFragment;
import com.divetym.dive.models.DailyTrip;

/**
 * Created by kali_root on 5/3/2017.
 */

public class TripDetailsActivity extends AuthenticatedActivity {

    public static void launch(DiveTymActivity context, DailyTrip dailyTrip) {
        Intent intent = new Intent(context, TripDetailsActivity.class);
        intent.putExtra(TripDetailsFragment.EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        DailyTrip dailyTrip = getIntent().getParcelableExtra(TripDetailsFragment.EXTRA_DAILY_TRIP);
        showBackButton(true);
        setTitle(dailyTrip.getDateOnly());
        initFragment(R.id.content, TripDetailsFragment.getInstance(dailyTrip));
    }

}
