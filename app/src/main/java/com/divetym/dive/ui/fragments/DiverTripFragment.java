package com.divetym.dive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.common.SortOption;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.DailyTripListResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.adapters.DiverTripAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.ui.view.ToastAlert;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 6/11/2017.
 */

public class DiverTripFragment extends DiveTymListFragment<DiverTripAdapter, DailyTrip, DailyTripListResponse> {

    private static final String TAG = DiverTripFragment.class.getSimpleName();
    private String mStartDate;
    private String mEndDate;
    private int mDiveSiteId;
    private boolean reset;
    private LatLng mLocationLatLng = new LatLng(0, 0);
    private SortOption mSortOption = new SortOption(SortOption.Order.date, SortOption.Sort.ASC);

    @Override
    protected void onFabClicked() {

    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new DiverTripAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean refresh = false;

        switch (item.getItemId()) {
            case R.id.menu_sort_date:
                refresh = true;
                mSortOption.setOrder(SortOption.Order.date);
                mSortOption.setSort(SortOption.Sort.ASC);
                break;
            case R.id.menu_sort_price_low_high:
                refresh = true;
                mSortOption.setOrder(SortOption.Order.price);
                mSortOption.setSort(SortOption.Sort.ASC);
                break;
            case R.id.menu_sort_price_high_low:
                refresh = true;
                mSortOption.setOrder(SortOption.Order.price);
                mSortOption.setSort(SortOption.Sort.DESC);
                break;
            case R.id.menu_sort_rating_low_high:
            case R.id.menu_sort_rating_high_low:
                // TODO: 5/24/2017 rating
                return true;
        }
        if (refresh) {
            item.setChecked(true);
            reset = true;

            requestData();
            return true;
        }
        return false;
    }

    @Override
    protected void requestData() {
        ApiClient.getApiInterface().getTrips(mStartDate, mEndDate, mDiveSiteId, mLocationLatLng.latitude, mLocationLatLng.longitude, offset,
                mSortOption.getSort().name(), mSortOption.getOrder().name())
                .enqueue(new Callback<DailyTripListResponse>() {
                    @Override
                    public void onResponse(Call<DailyTripListResponse> call, Response<DailyTripListResponse> response) {
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DailyTripListResponse> call, Throwable t) {
                        showProgress(false);
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                        Log.e(TAG, "getData onFailiure: " + t.getMessage());
                    }
                });
    }

    @Override
    protected void onRequestResponse(DailyTripListResponse response) {
        if (response != null && !response.isError()) {
            Log.d(TAG, "reset: " + reset + " response: " + response.toString());
            if (reset) {
                mAdapter.replaceData(response.getDailyTrips());
            } else {
                mAdapter.addData(response.getDailyTrips());
            }
        } else if (response != null) {
            ToastAlert.makeText(mContext, response.getMessage(), ToastAlert.LENGTH_SHORT);
        }
        setEmpty(mDataList.size() == 0);
    }

    @Override
    public void onItemClick(DailyTrip object, View view, int i) {
        Log.d(TAG, "onItemClick: " + object);
    }

    public void refreshTripList(String startDate, String endDate, LatLng locationLatLng, int diveSiteId) {
        mStartDate = startDate;
        mEndDate = endDate;
        mLocationLatLng = locationLatLng;
        mDiveSiteId = diveSiteId;
        offset = 0;
        reset = true;
        requestData();
    }
}
