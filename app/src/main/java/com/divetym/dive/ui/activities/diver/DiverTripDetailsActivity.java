package com.divetym.dive.ui.activities.diver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.fragments.diver.DiverTripDetailsFragment;
import com.divetym.dive.ui.view.ImageSlider;
import com.divetym.dive.ui.view.ToastAlert;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kali_root on 6/15/2017.
 */

public class DiverTripDetailsActivity extends DiveTymActivity {

    private static final String TAG = DiverTripDetailsActivity.class.getSimpleName();
    public static final String EXTRA_DAILY_TRIP = "com.divetym.dive.EXTRA_DAILY_TRIP";
    @BindView(R.id.image_slider)
    ImageSlider mImageSlider;

    @OnClick(R.id.fab)
    public void onFavoriteButtonClick(View view) {
        Log.d(TAG, "onFavoriteButtonClick: ");
        Snackbar.make(view, "Favorite button click!", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.button_book_now)
    public void onBookClick(View view) {
        Log.d(TAG, "onBookClick: ");
        Snackbar.make(view, "Book button click!", Snackbar.LENGTH_LONG)
                .show();
    }

    public static void launch(DiveTymActivity context, DailyTrip dailyTrip) {
        Intent intent = new Intent(context, DiverTripDetailsActivity.class);
        intent.putExtra(EXTRA_DAILY_TRIP, dailyTrip);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diver_trip_details);
        showBackButton(true);
        ButterKnife.bind(this);
        DailyTrip dailyTrip = getIntent().getParcelableExtra(EXTRA_DAILY_TRIP);
        initFragment(R.id.content, DiverTripDetailsFragment.newInstance(dailyTrip));
        mImageSlider.setDataList(dailyTrip.getSites());
        setTitle(dailyTrip.getDateOnly());
    }
}
