package com.divetym.dive.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.event.DailyTripEvent;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.ui.activities.base.AuthenticatedActivity;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.activities.common.Mode;
import com.divetym.dive.ui.fragments.TripDetailsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 5/3/2017.
 */

public class TripDetailsActivity extends AuthenticatedActivity {
    private static final String TAG = TripDetailsActivity.class.getSimpleName();
    public static final String EXTRA_DAILY_TRIP = "com.divetym.dive.EXTRA_DAILY_TRIP";
    public static final int REQUEST_EDIT = 1;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.image_collapsing_toolbar_background)
    protected ImageView toolbarBackgroundImage;
    private TripDetailsFragment mFragment;

    public static void launch(DiveTymActivity context, DailyTrip dailyTrip) {
        Intent intent = new Intent(context, TripDetailsActivity.class);
        intent.putExtra(EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivityForResult(intent, DiveShopTripActivity.REQUEST_REFRESH);
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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDailyTripChanged(DailyTripEvent event) {
        Log.d(TAG, "onDailyTripChanged: " + event);
        mFragment.setDailyTrip(event.getDailyTrip());
    }

}
