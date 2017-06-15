package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.ui.fragments.base.DiveTymFragment;
import com.divetym.dive.ui.view.GuidePreviewLayout;
import com.divetym.dive.ui.view.InfoLayout;
import com.divetym.dive.ui.view.ListPreviewLayout;
import com.divetym.dive.ui.view.RobotoTextView;

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
    @BindView(R.id.image_map_snapshot)
    ImageView mapSnapshotImage;
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


    @OnClick(R.id.button_view_daily_trips)
    public void onViewDailyTripClick() {

    }

    @OnClick(R.id.layout_review)
    public void onReviewsClick() {
        Toast.makeText(mContext, "Review click!", Toast.LENGTH_SHORT).show();
    }

    private DiveShop mDiveShop;

    public static DiverDiveShopFragment newInstance(DiveShop diveShop) {
        DiverDiveShopFragment fragment = new DiverDiveShopFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_DIVE_SHOP, diveShop);
        fragment.setArguments(bundle);
        return fragment;
    }

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
        return view;
    }
    private void loadDiveShopData() {
        if (mDiveShop == null) return;
        GlideApp.with(mContext)
                .load(null)// TODO: 6/15/2017 add mapshopshot on diveshop data
                .placeholder(R.drawable.dummy_image_preview)
                .error(R.drawable.dummy_image_error)
                .thumbnail(0.1f)
                .into(mapSnapshotImage);

        ratingMarkText.setText("9.2 Excellent");
        numberOfReviewText.setText("193 reviews");
        addressText.setText(mDiveShop.getAddress());
        currencyText.setText("PHP");// TODO: 6/15/2017 mapsnapshot, curreny helper,  moneyutils
        priceText.setText(mDiveShop.getPricePerDive().toString() + "/Dive");

        infoDescription.setInfoBody(mDiveShop.getDescription());
        infoSpecialService.setInfoBody(mDiveShop.getSpecialService());

        previewCourses.setPreviewList(mDiveShop.getCoursePreviews(false));
        previewBoats.setPreviewList(mDiveShop.getBoatPreviews(false));
        previewGuide.setGuides(mDiveShop.getGuides());
    }

}
