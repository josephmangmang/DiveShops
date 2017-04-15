package com.divetym.dive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.activities.CourseActivity;
import com.divetym.dive.activities.MainActivity;
import com.divetym.dive.activities.base.AuthenticatedActivity;
import com.divetym.dive.activities.base.DiveTymFragment;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.models.response.DiveShopResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.rest.ApiInterface;
import com.divetym.dive.view.ListPreviewLayout;
import com.divetym.dive.view.RobotoTextView;
import com.divetym.dive.view.ToastAlert;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/5/2017.
 */

public class DiveShopFragment extends DiveTymFragment {
    private static final String TAG = DiveShopFragment.class.getSimpleName();

    @BindView(R.id.button_view_daily_trips)
    Button btnViewDailyTrips;
    @BindView(R.id.text_description)
    RobotoTextView tvDescription;
    @BindView(R.id.text_price_per_dive)
    RobotoTextView tvPricePerDive;
    @BindView(R.id.preview_courses)
    ListPreviewLayout mPreviewCourses;
    @BindView(R.id.preview_boats)
    ListPreviewLayout mPreviewBoats;
    @BindView(R.id.text_special_service)
    RobotoTextView tvSpecialService;
    private ApiInterface mApiService;
    private DiveShop mDiveShop;

    private View.OnClickListener mPreviewCoursesMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CourseActivity.launch(mContext, mDiveShop.getCourses());
        }
    };
    private View.OnClickListener mPreviewBoatsMoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO: 4/9/2017 Start list of boats activity
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = ApiClient.getApiInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dive_shop_profile, container, false);
        ButterKnife.bind(this, view);
        loadDiveShopData();
        return view;
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
        ((MainActivity) mContext).setToolbarTitle(diveShop.getName());
        ((MainActivity) mContext).setToolbarSubtitle(diveShop.getAddress());
        tvDescription.setText(diveShop.getDescription());
        tvPricePerDive.setText(diveShop.getPricePerDive().toString());
        tvSpecialService.setText(diveShop.getSpecialService());

        mPreviewCourses.setPreviewTitle(getString(R.string.title_courses));
        mPreviewCourses.setPreviewList(diveShop.getCoursePreviews());
        mPreviewCourses.setMoreClickListener(mPreviewCoursesMoreClickListener);

        mPreviewBoats.setPreviewTitle(getString(R.string.title_boats));
        mPreviewBoats.setPreviewList(diveShop.getBoatPreviews());
        mPreviewBoats.setMoreClickListener(mPreviewBoatsMoreClickListener);
    }
}
