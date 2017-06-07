package com.divetym.dive.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.divetym.dive.R;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.activities.common.Mode;
import com.divetym.dive.fragments.TripDetailsFragment;
import com.divetym.dive.models.DailyTrip;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 5/3/2017.
 */

public class TripDetailsActivity extends AuthenticatedActivity {
    public static final String EXTRA_DAILY_TRIP = "com.divetym.dive.EXTRA_DAILY_TRIP";
    public static final int REQUEST_EDIT = 1;

    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.image_collapsing_toolbar_background)
    protected ImageView mToolbarBackground;
    private TripDetailsFragment mFragment;
    private boolean edited;

    public static void launch(DiveTymActivity context, DailyTrip dailyTrip) {
        Intent intent = new Intent(context, TripDetailsActivity.class);
        intent.putExtra(EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivityForResult(intent, DailyTripActivity.REQUEST_REFRESH);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        final DailyTrip dailyTrip = getIntent().getParcelableExtra(EXTRA_DAILY_TRIP);
        showBackButton(true);
        setTitle(dailyTrip.getDateOnly());
        Picasso.with(this)
                .load(dailyTrip.getSites().get(0).getImageUrl())
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(mToolbarBackground);
        mFragment = initFragment(R.id.content, TripDetailsFragment.getInstance(dailyTrip));
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyTripActivity.launch(TripDetailsActivity.this, Mode.EDIT, dailyTrip);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            edited = true;
            DailyTrip newDailyTrip = data.getParcelableExtra(EXTRA_DAILY_TRIP);
            mFragment.setDailyTrip(newDailyTrip);
        }
    }

    @Override
    public void onBackPressed() {
        if (edited) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
