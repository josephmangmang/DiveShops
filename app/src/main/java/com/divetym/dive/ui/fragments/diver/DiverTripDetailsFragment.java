package com.divetym.dive.ui.fragments.diver;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.divetym.dive.R;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DailyTripDiveSite;
import com.divetym.dive.models.DailyTripGuide;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.ui.activities.BoatDetailsActivity;
import com.divetym.dive.ui.activities.BoatListActivity;
import com.divetym.dive.ui.activities.GuideDetailsActivity;
import com.divetym.dive.ui.activities.GuideListActivity;
import com.divetym.dive.ui.activities.diver.DiverDiveShopActivity;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.GuidePreviewLayout;
import com.divetym.dive.ui.view.ListPreviewLayout;
import com.divetym.dive.ui.view.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.divetym.dive.ui.activities.diver.DiverTripDetailsActivity.EXTRA_DAILY_TRIP;

/**
 * Created by kali_root on 6/15/2017.
 */

public class DiverTripDetailsFragment extends DiveTymFragment {
    private DailyTrip mDailyTrip;
    @BindView(R.id.text_dive_shop)
    RobotoTextView diveShopNameText;
    @BindView(R.id.text_time)
    RobotoTextView timeText;
    @BindView(R.id.text_price)
    RobotoTextView priceText;
    @BindView(R.id.text_number_of_dives)
    RobotoTextView numberOfDivesText;
    @BindView(R.id.text_group_size)
    RobotoTextView groupSizeText;
    @BindView(R.id.text_remaining_slot)
    RobotoTextView remainingSlotText;
    @BindView(R.id.preview_dive_sites)
    ListPreviewLayout diveSitePreviewLayout;
    @BindView(R.id.preview_boats)
    ListPreviewLayout boatPreviewLayout;
    @BindView(R.id.preview_guides)
    GuidePreviewLayout guidePreviewLayout;

    @OnClick(R.id.text_dive_shop)
    public void onDiveShopNameClick() {
        DiverDiveShopActivity.launch(mContext, mDailyTrip.getDiveShopUid());
    }


    private BaseRecyclerAdapter.ItemClickListener<ListPreview> mBoatPreviewClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            BoatDetailsActivity.launch(mContext, mDailyTrip.getBoats().get(object.getPosition()));
        }
    };
    private BaseRecyclerAdapter.ItemClickListener mSitePreviewClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(Object object, View view, int position) {

        }
    };
    private BaseRecyclerAdapter.ItemClickListener mGuidePreviewClickListener = new ItemClickListener<DailyTripGuide>() {
        @Override
        public void onItemClick(DailyTripGuide object, View view, int i) {
            GuideDetailsActivity.launch(mContext, mDailyTrip.getGuides().get(i));
        }
    };

    public static DiverTripDetailsFragment newInstance(DailyTrip dailyTrip) {
        DiverTripDetailsFragment fragment = new DiverTripDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_diver_trip_details, container, false);
        ButterKnife.bind(this, view);
        loadTripData();
        diveSitePreviewLayout.setItemClickListener(mSitePreviewClickListener);
        diveSitePreviewLayout.setMoreClickListener(view13 -> {
            if (!diveSitePreviewLayout.isEmpty()) {
                // TODO: 6/10/2017 open dive site list
            }
        });
        boatPreviewLayout.setItemClickListener(mBoatPreviewClickListener);
        boatPreviewLayout.setMoreClickListener(view12 -> {
            if (!boatPreviewLayout.isEmpty()) {
                BoatListActivity.launch(mContext, mDailyTrip.getBoats(), false);
            }
        });
        guidePreviewLayout.setItemClickListener(mGuidePreviewClickListener);
        guidePreviewLayout.setMoreClickListener(view1 -> {
            if (!guidePreviewLayout.isEmpty()) {
                GuideListActivity.launch(mContext, mDailyTrip.getGuides(), false);
            }
        });
        return view;
    }

    private void loadTripData() {
        diveShopNameText.setText(mDailyTrip.getDiveShopName());
        priceText.setText("PHP " + mDailyTrip.getPrice().toString() + "/Dive");
        timeText.setText(mDailyTrip.getTimeOnly());
        numberOfDivesText.setText("" + mDailyTrip.getNumberOfDive());
        groupSizeText.setText("" + mDailyTrip.getGroupSize());
        remainingSlotText.setText("" + mDailyTrip.getRemainingSlot());

        diveSitePreviewLayout.setPreviewList(mDailyTrip.getDiveSitePreviews());
        boatPreviewLayout.setPreviewList(mDailyTrip.getBoatPreviews());
        guidePreviewLayout.setGuides(mDailyTrip.getGuides());
    }
}
