package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.activities.common.Mode;
import com.divetym.dive.ui.fragments.TripDetailsFragment;
import com.divetym.dive.models.DailyTrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 5/3/2017.
 */

public class TripDetailsActivity extends AuthenticatedActivity {
    public static final String EXTRA_DAILY_TRIP = "com.divetym.dive.EXTRA_DAILY_TRIP";
    public static final int REQUEST_EDIT = 1;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.image_collapsing_toolbar_background)
    protected ImageView toolbarBackgroundImage;
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
        GlideApp.with(this)
                .load(dailyTrip.getSites().get(0).getImageUrl())
                .thumbnail(0.1f)
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .into(toolbarBackgroundImage);
        mFragment = initFragment(R.id.content, TripDetailsFragment.getInstance(dailyTrip));
        fab.setOnClickListener(view -> ModifyTripActivity.launch(TripDetailsActivity.this, Mode.EDIT, dailyTrip));
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
