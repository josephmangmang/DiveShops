package com.divetym.dive.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DailyTripBoat;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.view.ListPreviewLayout;
import com.divetym.dive.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kali_root on 5/5/2017.
 */

public class TripDetailsFragment extends DiveTymFragment {

    public static final String TAG = TripDetailsFragment.class.getSimpleName();
    public static final String EXTRA_DAILY_TRIP = "com.divetym.dive.EXTRA_DAILY_TRIP";

    @BindView(R.id.text_time)
    RobotoTextView tvTime;
    @BindView(R.id.text_date)
    RobotoTextView tvDate;
    @BindView(R.id.text_location)
    RobotoTextView tvLocation;
    @BindView(R.id.text_guides)
    RobotoTextView tvGuides;
    @BindView(R.id.text_group_size)
    RobotoTextView tvGroupSize;
    @BindView(R.id.text_remaining_slot)
    RobotoTextView tvRemainingSlot;
    @BindView(R.id.preview_trip_boats)
    ListPreviewLayout mPreviewTripBoats;
    @BindView(R.id.text_total_price)
    RobotoTextView tvTotalPrice;
    @BindView(R.id.text_price_summary)
    RobotoTextView tvPriceSummary;
    @BindView(R.id.text_guests)
    RobotoTextView tvTripGuests;

    private ApiInterface mApiService;
    private DailyTrip mDailyTrip;
    
    private BaseRecyclerAdapter.ItemClickListener<ListPreview> mPreviewItemClickListener = new BaseRecyclerAdapter.ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view) {

        }

        @Override
        public void onActionClick(ListPreview object, View view) {

        }
    };

    public static Fragment getInstance(DailyTrip dailyTrip) {
        Fragment fragment = new TripDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DAILY_TRIP, dailyTrip);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = ApiClient.getApiInterface();
        mDailyTrip = getArguments().getParcelable(EXTRA_DAILY_TRIP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        ButterKnife.bind(this, view);
        loadDailyTripData();
        return view;
    }

    private void loadDailyTripData() {
        tvDate.setText(mDailyTrip.getDateOnly());
        tvTime.setText(mDailyTrip.getTimeOnly());
        tvGroupSize.setText("" + mDailyTrip.getGroupSize());
        tvLocation.setText(mDailyTrip.getDiveSites());
        tvGuides.setText(mDailyTrip.getGuideNames());
        tvTotalPrice.setText(mDailyTrip.getPrice().toString());
        tvPriceSummary.setText(mDailyTrip.getPriceNote());
        tvRemainingSlot.setText("" + mDailyTrip.getRemainingSlot());
        tvTripGuests.setText(mDailyTrip.getGuestNames());

        mPreviewTripBoats.setItemClickListener(mPreviewItemClickListener);
        mPreviewTripBoats.setMoreClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "More clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        mPreviewTripBoats.setPreviewTitle(getString(R.string.title_boats));
        mPreviewTripBoats.setPreviewList(mDailyTrip.getBoatPreviews());

    }


}
