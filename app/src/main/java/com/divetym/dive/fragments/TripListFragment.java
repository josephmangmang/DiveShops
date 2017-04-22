package com.divetym.dive.fragments;

import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.adapters.TripListAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.DailyTripListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/22/2017.
 */

public class TripListFragment extends DiveTymListFragment<TripListAdapter, DailyTrip, DailyTripListResponse> {
    public static final String TAG = TripListFragment.class.getSimpleName();
    private String startDate;
    private String endDate;

    @Override
    protected void initializeAdapter() {
        mAdapter = new TripListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
    }

    @Override
    protected void requestData() {
        mApiService.getDiveShopTrips(mShopUid, startDate, endDate)
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    protected void onRequestResponse(DailyTripListResponse response) {
        if (response == null && !response.isError()) {
            mAdapter.addData(response.getDailyTrips());
        }
    }

    @Override
    public void onItemClick(DailyTrip object, View view) {
        super.onItemClick(object, view);
        Log.d(TAG, "onItemClick : " + object.toString());
    }
}
