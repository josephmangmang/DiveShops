package com.divetym.dive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.BoatDetailsActivity;
import com.divetym.dive.ui.activities.BoatListActivity;
import com.divetym.dive.ui.activities.GuideDetailsActivity;
import com.divetym.dive.ui.activities.GuideListActivity;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.ui.view.ListPreviewLayout;
import com.divetym.dive.ui.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.divetym.dive.ui.activities.TripDetailsActivity.EXTRA_DAILY_TRIP;

/**
 * Created by kali_root on 5/5/2017.
 */

public class TripDetailsFragment extends DiveTymFragment {

    public static final String TAG = TripDetailsFragment.class.getSimpleName();
    @BindView(R.id.text_time)
    RobotoTextView timeTextView;
    @BindView(R.id.text_date)
    RobotoTextView dateTextView;
    @BindView(R.id.text_number_of_dives)
    RobotoTextView numberOfDivesTextView;
    @BindView(R.id.text_group_size)
    RobotoTextView groupSizeTextView;
    @BindView(R.id.text_remaining_slot)
    RobotoTextView remainingSlotTextView;
    @BindView(R.id.preview_trip_sites)
    ListPreviewLayout mPreViewTripSites;
    @BindView(R.id.preview_trip_guides)
    ListPreviewLayout mPreviewTripGuides;
    @BindView(R.id.preview_trip_boats)
    ListPreviewLayout mPreviewTripBoats;
    @BindView(R.id.text_total_price)
    RobotoTextView totalPriceTextView;
    @BindView(R.id.text_price_summary)
    RobotoTextView priceSummaryTextView;
    @BindView(R.id.text_guests)
    RobotoTextView tripGuestsTextView;

    private DailyTrip mDailyTrip;

    private BaseRecyclerAdapter.ItemClickListener<ListPreview> mPreviewBoatClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            BoatDetailsActivity.launch(mContext, mDailyTrip.getBoats().get(object.getPosition()));
        }

        @Override
        public void onActionClick(ListPreview object, View view) {
            BoatDetailsActivity.launch(mContext, mDailyTrip.getBoats().get(object.getPosition()));
        }
    };
    private BaseRecyclerAdapter.ItemClickListener mPreviewSiteClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(Object object, View view, int position) {

        }

        @Override
        public void onActionClick(Object object, View view) {

        }
    };
    private BaseRecyclerAdapter.ItemClickListener mPreviewGuideClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            GuideDetailsActivity.launch(mContext, mDailyTrip.getGuides().get(object.getPosition()));
        }
    };

    public static TripDetailsFragment getInstance(DailyTrip dailyTrip) {
        TripDetailsFragment fragment = new TripDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DAILY_TRIP, dailyTrip);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDailyTrip = getArguments().getParcelable(EXTRA_DAILY_TRIP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_details, container, false);
        ButterKnife.bind(this, view);

        loadDailyTripData();

        mPreViewTripSites.setItemClickListener(mPreviewSiteClickListener);
        mPreViewTripSites.setMoreClickListener(view13 -> {
            if (!mPreViewTripSites.isEmpty()) {
                // TODO: 6/10/2017 open dive site list
            }
        });
        mPreviewTripGuides.setItemClickListener(mPreviewGuideClickListener);
        mPreviewTripGuides.setMoreClickListener(view1 -> {
            if (!mPreviewTripGuides.isEmpty()) {
                GuideListActivity.launch(mContext, mDailyTrip.getGuides(), false);
            }
        });
        mPreviewTripBoats.setItemClickListener(mPreviewBoatClickListener);
        mPreviewTripBoats.setMoreClickListener(view12 -> {
            if (!mPreviewTripBoats.isEmpty()) {
                BoatListActivity.launch(mContext, mDailyTrip.getBoats(), false);
            }
        });
        return view;
    }

    private void loadDailyTripData() {
        dateTextView.setText(mDailyTrip.getDateOnly());
        timeTextView.setText(mDailyTrip.getTimeOnly());
        numberOfDivesTextView.setText("" + mDailyTrip.getNumberOfDive());
        groupSizeTextView.setText("" + mDailyTrip.getGroupSize());
        totalPriceTextView.setText(mDailyTrip.getPrice().toString());
        priceSummaryTextView.setText(mDailyTrip.getPriceNote());
        remainingSlotTextView.setText("" + mDailyTrip.getRemainingSlot());
        tripGuestsTextView.setText(mDailyTrip.getGuestNames());

        mPreViewTripSites.setPreviewTitle(getString(R.string.title_destinations));
        mPreViewTripSites.setPreviewList(mDailyTrip.getDiveSitePreviews());

        mPreviewTripGuides.setPreviewTitle(getString(R.string.title_guides));
        mPreviewTripGuides.setPreviewList(mDailyTrip.getGuidePreviews());

        mPreviewTripBoats.setPreviewTitle(getString(R.string.title_boats));
        mPreviewTripBoats.setPreviewList(mDailyTrip.getBoatPreviews());

    }

    public void setDailyTrip(DailyTrip dailyTrip) {
        mDailyTrip = dailyTrip;
        loadDailyTripData();
    }
}
