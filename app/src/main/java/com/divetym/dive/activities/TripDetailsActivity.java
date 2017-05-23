package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.activities.common.Mode;
import com.divetym.dive.fragments.TripDetailsFragment;
import com.divetym.dive.models.DailyTrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 5/3/2017.
 */

public class TripDetailsActivity extends AuthenticatedActivity {
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    public static void launch(DiveTymActivity context, DailyTrip dailyTrip) {
        Intent intent = new Intent(context, TripDetailsActivity.class);
        intent.putExtra(TripDetailsFragment.EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        final DailyTrip dailyTrip = getIntent().getParcelableExtra(TripDetailsFragment.EXTRA_DAILY_TRIP);
        showBackButton(true);
        setTitle(dailyTrip.getDateOnly());
        initFragment(R.id.content, TripDetailsFragment.getInstance(dailyTrip));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyTripActivity.launch(TripDetailsActivity.this, Mode.EDIT, dailyTrip);
            }
        });
    }

}
