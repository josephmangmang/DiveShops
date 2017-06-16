package com.divetym.dive.ui.fragments.diver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.common.SortOption;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.DailyTripListResponse;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.TripDetailsActivity;
import com.divetym.dive.ui.activities.diver.DiverTripDetailsActivity;
import com.divetym.dive.ui.adapters.TripListAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.ui.view.ToastAlert;

import retrofit2.Call;
import retrofit2.Callback;

import static com.divetym.dive.common.SortOption.*;
import static com.divetym.dive.ui.activities.diver.DiverDiveShopActivity.*;

/**
 * Created by kali_root on 6/15/2017.
 */

public class DiverDiveShopTripFragment extends DiveTymListFragment<TripListAdapter, DailyTrip, DailyTripListResponse> {
    private static final String TAG = DiverDiveShopTripFragment.class.getSimpleName();
    private String mStartDate;
    private String mEndDate;
    private SortOption mSortOption = new SortOption(Order.date, Sort.ASC);
    private int mDiveSiteId;
    private boolean reset;

    public static DiverDiveShopTripFragment newInstance(@NonNull String shopUid) {
        DiverDiveShopTripFragment fragment = new DiverDiveShopTripFragment();
        Bundle bunddle = new Bundle();
        bunddle.putString(EXTRA_DIVE_SHOP_UID, shopUid);
        fragment.setArguments(bunddle);
        fragment.shopUid = shopUid;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        shopUid = getArguments().getString(EXTRA_DIVE_SHOP_UID);
        if (shopUid == null) {
            Toast.makeText(mContext, R.string.error_something_wrong, Toast.LENGTH_SHORT).show();
            mContext.finish();
        }
    }

    @Override
    protected void onFabClicked() {
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new TripListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
        showFab(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.daily_trips, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean refresh = false;

        switch (item.getItemId()) {
            case R.id.menu_sort_date:
                refresh = true;
                mSortOption.setOrder(Order.date);
                mSortOption.setSort(Sort.ASC);
                break;
            case R.id.menu_sort_price_low_high:
                refresh = true;
                mSortOption.setOrder(Order.price);
                mSortOption.setSort(Sort.ASC);
                break;
            case R.id.menu_sort_price_high_low:
                refresh = true;
                mSortOption.setOrder(Order.price);
                mSortOption.setSort(Sort.DESC);
                break;
            case R.id.menu_sort_rating_low_high:
            case R.id.menu_sort_rating_high_low:
                // TODO: 5/24/2017 rating
                return true;
        }
        if (refresh) {
            item.setChecked(true);
            refreshTripList(mStartDate, mEndDate, mDiveSiteId);
            return true;
        }
        return false;
    }

    @Override
    protected void requestData() {
        requestDiveShopTrips();
    }

    private void requestDiveShopTrips() {
        ApiClient.getApiInterface().getDiveShopTrips(shopUid, mDiveSiteId, mStartDate, mEndDate,
                offset, mSortOption.getSort().name(), mSortOption.getOrder().name())
                .enqueue(new Callback<DailyTripListResponse>() {
                    @Override
                    public void onResponse(Call<DailyTripListResponse> call, retrofit2.Response<DailyTripListResponse> response) {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "onResponse: " + response.toString());
                        }
                        showProgress(false);
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
    public void onLoadMore(int totalItemCount) {
        reset = false;
        super.onLoadMore(totalItemCount);
    }

    public void refreshTripList(String startDate, String endDate, int diveSiteId) {
        mStartDate = startDate;
        mEndDate = endDate;
        mDiveSiteId = diveSiteId;
        offset = 0;
        reset = true;
        requestData();
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
        Log.d(TAG, "onItemClick : " + object.toString());
        DiverTripDetailsActivity.launch(mContext, object);

    }

}
