package com.divetym.dive.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.adapters.TripListAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.response.DailyTripListResponse;
import com.divetym.dive.utils.DateUtils;
import com.divetym.dive.view.ToastAlert;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/22/2017.
 */

public class TripListFragment extends DiveTymListFragment<TripListAdapter, DailyTrip, DailyTripListResponse> {
    public static final String TAG = TripListFragment.class.getSimpleName();
    private String mStartDate;
    private String mEndDate;
    private int mDiveSiteId;
    private boolean reset;

    public interface OnRefreshTripListener {
        void onDateRangedChanged(Calendar startDate, Calendar endDate);

        void onDiveSiteChanged(DiveSite diveSite);
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new TripListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    @Override
    protected void requestData() {
        // TODO: 4/24/2017 two types of request.. diveshop trip or trips
        requestDiveShopTrips();
    }

    private void requestDiveShopTrips() {
        mApiService.getDiveShopTrips(mShopUid, mDiveSiteId, mStartDate, mEndDate)
                .enqueue(new Callback<DailyTripListResponse>() {
                    @Override
                    public void onResponse(Call<DailyTripListResponse> call, Response<DailyTripListResponse> response) {
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DailyTripListResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                        Log.e(TAG, "requestData onFailiure: " + t.getMessage());
                    }
                });
    }

    public void refreshTripList(@NonNull DiveSite diveSite, Date startDate, Date endDate) {
        mOffset = 0;
        reset = true;
        if (diveSite != null) {
            mDiveSiteId = diveSite.getDiveSiteId();
        } else {
            mDiveSiteId = -1;
        }
        mStartDate = DateUtils.formatServerDate(startDate);
        mEndDate = DateUtils.formatServerDate(endDate);
        requestData();
    }

    @Override
    protected void onRequestResponse(DailyTripListResponse response) {
        if (response != null && !response.isError()) {
            Log.d(TAG, response.toString());
            if(reset) {
                mAdapter.replaceData(response.getDailyTrips());
            }else{
                mAdapter.addData(response.getDailyTrips());
            }
        } else if (response != null) {
            ToastAlert.makeText(mContext, response.getMessage(), ToastAlert.LENGTH_SHORT);
        }
        setEmpty(mDataList.size() == 0);
    }

    @Override
    public void onItemClick(DailyTrip object, View view) {
        super.onItemClick(object, view);
        Log.d(TAG, "onItemClick : " + object.toString());
    }
}
