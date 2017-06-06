package com.divetym.dive.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.activities.BoatDetailsActivity;
import com.divetym.dive.activities.BoatListActivity;
import com.divetym.dive.activities.CourseListActivity;
import com.divetym.dive.activities.CourseDetailsActivity;
import com.divetym.dive.activities.DailyTripActivity;
import com.divetym.dive.activities.EditDiveShopActivity;
import com.divetym.dive.activities.GuideListActivity;
import com.divetym.dive.activities.MainActivity;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.fragments.base.DiveTymFragment;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.ListPreview;
import com.divetym.dive.models.response.DiveShopResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.view.ListPreviewLayout;
import com.divetym.dive.view.RobotoTextView;
import com.divetym.dive.view.ToastAlert;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kali_root on 4/5/2017.
 */

public class DiveShopFragment extends DiveTymFragment {
    private static final String TAG = DiveShopFragment.class.getSimpleName();
    public static final int REQUEST_UPDATE = 1;
    public static final String EXTRA_DIVE_SHOP = "com.divetym.dive.EXTRA_DIVE_SHOP";
    @BindView(R.id.text_description)
    RobotoTextView tvDescription;
    @BindView(R.id.text_price_per_dive)
    RobotoTextView tvPricePerDive;
    @BindView(R.id.preview_courses)
    ListPreviewLayout mPreviewCourses;
    @BindView(R.id.preview_boats)
    ListPreviewLayout mPreviewBoats;
    @BindView(R.id.preview_guides)
    ListPreviewLayout mPreviewGuides;
    @BindView(R.id.text_special_service)
    RobotoTextView tvSpecialService;
    private ApiInterface mApiService;
    private DiveShop mDiveShop;

    private BaseRecyclerAdapter.ItemClickListener mGuideItemClickListener = new ItemClickListener<ListPreview>() {
        public void onItemClick(ListPreview object, View view, int i) {
            Log.d(TAG, "onItemClick " + object.toString());
        }

        @Override
        public void onActionClick(ListPreview object, View view) {
            Log.d(TAG, "onActionClick " + object.toString());
        }
    };
    private View.OnClickListener mPreviewGuideMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GuideListActivity.launch(mContext, mDiveShop.getGuides(), true);
        }
    };

    @OnClick(R.id.button_view_daily_trips)
    public void onViewDailyTrip() {
        startActivity(new Intent(mContext, DailyTripActivity.class));
    }

    private View.OnClickListener mPreviewCoursesMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CourseListActivity.launch(mContext, mDiveShop.getCourses(), true);
        }
    };
    private View.OnClickListener mPreviewBoatsMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BoatListActivity.launch(mContext, mDiveShop.getBoats(), true);
        }
    };
    private BaseRecyclerAdapter.ItemClickListener mCourseItemClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            Log.d(TAG, "onItemClick " + object.toString());
            CourseDetailsActivity.launch(mContext, mDiveShop.getCourses().get(object.getPosition()));
        }

        @Override
        public void onActionClick(ListPreview object, View view) {
            Log.d(TAG, "onActionClick " + object.toString());
        }
    };

    private BaseRecyclerAdapter.ItemClickListener mBoatItemClickListener = new ItemClickListener<ListPreview>() {
        @Override
        public void onItemClick(ListPreview object, View view, int i) {
            Log.d(TAG, "onItemClick " + object.toString());
            BoatDetailsActivity.launch(mContext, mDiveShop.getBoats().get(object.getPosition()));
        }

        @Override
        public void onActionClick(ListPreview object, View view) {
            Log.d(TAG, "onActionClick " + object.toString());

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mApiService = ApiClient.getApiInterface();
        mDiveShop = new DiveShop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dive_shop_profile, container, false);
        ButterKnife.bind(this, view);

        mPreviewCourses.setMoreClickListener(mPreviewCoursesMoreClickListener);
        mPreviewCourses.setItemClickListener(mCourseItemClickListener);
        mPreviewBoats.setItemClickListener(mBoatItemClickListener);
        mPreviewBoats.setMoreClickListener(mPreviewBoatsMoreClickListener);
        mPreviewGuides.setItemClickListener(mGuideItemClickListener);
        mPreviewGuides.setMoreClickListener(mPreviewGuideMoreClickListener);

        loadDiveShopData();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            mDiveShop = data.getParcelableExtra(EXTRA_DIVE_SHOP);
            setDiveshop(mDiveShop);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            onEditClicked();
        }
        return false;
    }

    private void onEditClicked() {
        EditDiveShopActivity.launch(this, mDiveShop, REQUEST_UPDATE);
    }


    private void loadDiveShopData() {
        String uid = mSessionManager.getUid();
        if (uid == null) {
            new ToastAlert(mContext)
                    .setMessage(R.string.error_empty_account_details)
                    .show();
            ((AuthenticatedActivity) mContext).logOut();
            return;
        }
        String shopUid = mSessionManager.getDiveShopUid();
        if (shopUid != null) {
            mApiService.getDiveShop(shopUid)
                    .enqueue(new Callback<DiveShopResponse>() {
                        @Override
                        public void onResponse(Call<DiveShopResponse> call, Response<DiveShopResponse> response) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            DiveShopResponse body = response.body();
                            if (body != null && !body.isError()) {
                                setDiveshop(body.getDiveShop());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiveShopResponse> call, Throwable t) {
                            Log.e(TAG, "Failed getting user account: " + t.getMessage());
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, call.request().toString());
                                t.printStackTrace();
                            }
                            showToastAlert(t.getMessage());
                        }
                    });
        } else {
            mApiService.getDiveShopByUid(uid)
                    .enqueue(new Callback<DiveShopResponse>() {
                        @Override
                        public void onResponse(Call<DiveShopResponse> call, Response<DiveShopResponse> response) {
                            Log.d(TAG, "onResponse: " + response.toString());
                            DiveShopResponse body = response.body();
                            if (body != null && !body.isError()) {
                                setDiveshop(body.getDiveShop());
                            }
                        }

                        @Override
                        public void onFailure(Call<DiveShopResponse> call, Throwable t) {
                            Log.e(TAG, "Failed getting user account: " + t.getMessage());
                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, call.request().toString());
                                t.printStackTrace();
                            }
                            showToastAlert(t.getMessage());
                        }
                    });
        }
    }

    private void setDiveshop(DiveShop diveShop) {
        Log.d(TAG, "Dive_Shop: " + diveShop.toString());
        mDiveShop = diveShop;
        mSessionManager.setDiveShopUid(diveShop.getDiveShopUid());

        mContext.setTitle(diveShop.getName());
        mContext.setSubtitle(diveShop.getAddress());
        MainActivity mainActivity = ((MainActivity) mContext);
        mainActivity.setToolbarTitle(diveShop.getName());
        mainActivity.setToolbarSubtitle(diveShop.getAddress());
        mainActivity.setToolbarBackground(diveShop.getImageUrl());
        tvDescription.setText(diveShop.getDescription());
        tvPricePerDive.setText(diveShop.getPricePerDive().toString());
        tvSpecialService.setText(diveShop.getSpecialService());

        mPreviewCourses.setPreviewTitle(getString(R.string.title_courses));
        mPreviewCourses.setPreviewList(diveShop.getCoursePreviews());

        mPreviewBoats.setPreviewTitle(getString(R.string.title_boats));
        mPreviewBoats.setPreviewList(diveShop.getBoatPreviews());

        mPreviewGuides.setPreviewTitle(getString(R.string.title_guides));
        mPreviewGuides.setPreviewList(diveShop.getGuidePreviews());
    }
}
