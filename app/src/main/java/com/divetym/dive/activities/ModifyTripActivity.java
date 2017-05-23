package com.divetym.dive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.activities.common.Mode;
import com.divetym.dive.fragments.CreateTripFragment;
import com.divetym.dive.fragments.EditTripFragment;
import com.divetym.dive.models.DailyTrip;

/**
 * Created by kali_root on 5/9/2017.
 */

public class ModifyTripActivity extends AuthenticatedActivity {

    public static final String EXTRA_DAILY_TRIP = "extra_daily_trip";

    public static void launch(DiveTymActivity context, Mode actionMode, @Nullable DailyTrip dailyTrip) {
        Intent intent = new Intent(context, ModifyTripActivity.class);
        intent.setAction(actionMode.name());
        intent.putExtra(EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        showBackButton(true);

        switch (Mode.valueOf(getIntent().getAction())) {
            case CREATE:
                initFragment(R.id.content, new CreateTripFragment());
                break;
            case EDIT:
                DailyTrip dailyTrip = getIntent().getParcelableExtra(EXTRA_DAILY_TRIP);
                initFragment(R.id.content, EditTripFragment.getInstance(dailyTrip));
                break;
            default:
                Toast.makeText(this, "No valid action specified. Finishing...", Toast.LENGTH_SHORT).show();
                finish();
        }
    }
}
