package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.activities.common.Mode;
import com.divetym.dive.ui.fragments.CreateTripFragment;
import com.divetym.dive.ui.fragments.EditTripFragment;
import com.divetym.dive.models.DailyTrip;

import static com.divetym.dive.ui.activities.TripDetailsActivity.EXTRA_DAILY_TRIP;
import static com.divetym.dive.ui.activities.TripDetailsActivity.REQUEST_EDIT;

/**
 * Created by kali_root on 5/9/2017.
 */

public class ModifyTripActivity extends AuthenticatedActivity {

    public static void launch(DiveTymActivity context, Mode actionMode, @Nullable DailyTrip dailyTrip) {
        Intent intent = new Intent(context, ModifyTripActivity.class);
        intent.setAction(actionMode.name());
        intent.putExtra(EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivityForResult(intent, REQUEST_EDIT);
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
