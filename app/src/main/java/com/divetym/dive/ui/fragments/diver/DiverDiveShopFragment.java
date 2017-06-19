package com.divetym.dive.ui.fragments.diver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.ui.activities.BoatDetailsActivity;
import com.divetym.dive.ui.activities.BoatListActivity;
import com.divetym.dive.ui.activities.CourseDetailsActivity;
import com.divetym.dive.ui.activities.CourseListActivity;
import com.divetym.dive.ui.activities.DiveShopTripActivity;
import com.divetym.dive.ui.activities.GuideDetailsActivity;
import com.divetym.dive.ui.activities.GuideListActivity;
import com.divetym.dive.ui.activities.diver.DiverDiveShopTripActivity;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.GuidePreviewLayout;
import com.divetym.dive.ui.view.InfoLayout;
import com.divetym.dive.ui.view.ListPreviewLayout;
import com.divetym.dive.ui.view.RobotoTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.divetym.dive.ui.activities.diver.DiverDiveShopActivity.*;

/**
 * Created by kali_root on 6/15/2017.
 */

public class DiverDiveShopFragment extends DiveTymFragment {

    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.text_rating_mark)
    RobotoTextView ratingMarkText; // 9.2 Execellent
    @BindView(R.id.text_number_of_review)
    RobotoTextView numberOfReviewText; // 239 reviews
    @BindView(R.id.text_address)
    RobotoTextView addressText;
    @BindView(R.id.text_price_currency)
    RobotoTextView currencyText;
    @BindView(R.id.text_price)
    RobotoTextView priceText;

    @BindView(R.id.preview_courses)
    ListPreviewLayout previewCourses;
    @BindView(R.id.preview_boats)
    ListPreviewLayout previewBoats;
    @BindView(R.id.preview_guides)
    GuidePreviewLayout previewGuide;
    @BindView(R.id.info_layout_shop_description)
    InfoLayout infoDescription;
    @BindView(R.id.info_layout_special_service)
    InfoLayout infoSpecialService;
    private DiveShop mDiveShop;

    @OnClick(R.id.button_view_daily_trips)
    public void onViewDailyTripClick() {
        DiverDiveShopTripActivity.launch(
                mContext,
                mDiveShop.getDiveShopUid(),
                mDiveShop.getName());
    }

    @OnClick(R.id.layout_review)
    public void onReviewsClick() {
        Toast.makeText(mContext, "Review click!", Toast.LENGTH_SHORT).show();
    }

    public static DiverDiveShopFragment newInstance(DiveShop diveShop) {
        DiverDiveShopFragment fragment = new DiverDiveShopFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DIVE_SHOP, diveShop);
        fragment.setArguments(bundle);
        return fragment;
    }

    private BaseRecyclerAdapter.ItemClickListener mCourseItemClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            CourseDetailsActivity.launch(mContext, mDiveShop.getCourses().get(object.getPosition()));
        }
    };

    private BaseRecyclerAdapter.ItemClickListener mBoatItemClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            BoatDetailsActivity.launch(mContext, mDiveShop.getBoats().get(object.getPosition()));
        }

    };
    private BaseRecyclerAdapter.ItemClickListener mGuideItemClickListener = new ItemClickListener<Guide>() {
        public void onItemClick(Guide object, View view, int i) {
            GuideDetailsActivity.launch(mContext, mDiveShop.getGuides().get(i));
        }
    };

    private View.OnClickListener mPreviewCoursesMoreClickListener = view -> {
        CourseListActivity.launch(mContext, mDiveShop.getCourses(), mDiveShop.getDiveShopUid(), false);
    };
    private View.OnClickListener mPreviewBoatsMoreClickListener = view -> {
        BoatListActivity.launch(mContext, mDiveShop.getBoats(), mDiveShop.getDiveShopUid(), false);
    };
    private View.OnClickListener mPreviewGuideMoreClickListener = view -> {
        GuideListActivity.launch(mContext, mDiveShop.getGuides(), mDiveShop.getDiveShopUid(), false);
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiveShop = getArguments().getParcelable(EXTRA_DIVE_SHOP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diver_dive_shop, container, false);
        ButterKnife.bind(this, view);
        loadDiveShopData();
        initListener();
        return view;
    }

    private void initListener() {
        previewCourses.setMoreClickListener(mPreviewCoursesMoreClickListener);
        previewCourses.setItemClickListener(mCourseItemClickListener);
        previewBoats.setItemClickListener(mBoatItemClickListener);
        previewBoats.setMoreClickListener(mPreviewBoatsMoreClickListener);
        previewGuide.setItemClickListener(mGuideItemClickListener);
        previewGuide.setMoreClickListener(mPreviewGuideMoreClickListener);

    }

    private void loadDiveShopData() {
        if (mDiveShop == null) return;
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_snapshot);
        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                if (googleMap != null) {
                    LatLng latLang = new LatLng(mDiveShop.getLatitiude(), mDiveShop.getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLang)
                            .title(mDiveShop.getAddress()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang, 13f));
                }
            });
        }
        ratingMarkText.setText("9.2 Excellent");
        numberOfReviewText.setText("193 reviews");
        addressText.setText(mDiveShop.getAddress());
        currencyText.setText("PHP");// TODO: 6/15/2017 curreny helper,  moneyutils
        priceText.setText(mDiveShop.getPricePerDive().toString() + "/Dive");

        infoDescription.setInfoBody(mDiveShop.getDescription());
        infoSpecialService.setInfoBody(mDiveShop.getSpecialService());

        previewCourses.setPreviewList(mDiveShop.getCoursePreviews(false));
        previewBoats.setPreviewList(mDiveShop.getBoatPreviews(false));
        previewGuide.setGuides(mDiveShop.getGuides());
    }

}
