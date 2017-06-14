package com.divetym.dive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.common.SortOption;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.response.DailyTripListResponse;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.ModifyTripActivity;
import com.divetym.dive.ui.activities.TripDetailsActivity;
import com.divetym.dive.ui.activities.common.Mode;
import com.divetym.dive.ui.adapters.TripListAdapter;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.ui.view.ToastAlert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.divetym.dive.common.SortOption.Order;
import static com.divetym.dive.common.SortOption.Sort;

/**
 * Created by kali_root on 4/22/2017.
 */

public class DiveShopTripFragment extends DiveTymListFragment<TripListAdapter, DailyTrip, DailyTripListResponse>
        implements BaseRecyclerAdapter.MultiSelectListener {
    public static final String TAG = DiveShopTripFragment.class.getSimpleName();
    private static final String BUNDLE_START_DATE = "bundle_start_date";
    private static final String BUNDLE_END_DATE = "bundle_end_date";
    private String mStartDate;
    private String mEndDate;
    private SortOption mSortOption = new SortOption(Order.date, Sort.ASC);
    private int mDiveSiteId;
    private boolean reset;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onFabClicked() {
        if (mAdapter.isInMultiSelectMode()) {
            showDeleteSelectedTripsDialog();
            return;
        }
        ModifyTripActivity.launch(mContext, Mode.CREATE, null);
    }

    public void inflateToolbar(Menu menu, MenuInflater inflater) {
        if (mAdapter.isInMultiSelectMode()) {
            inflater.inflate(R.menu.daily_trip_selection, menu);
            mContext.setTitle(getString(R.string.title_conversations_selected, mAdapter.getSelectedItems().size()));

        } else {
            mContext.setTitle(getString(R.string.title_daily_trips));
            inflater.inflate(R.menu.daily_trips, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean refresh = false;

        switch (item.getItemId()) {
            case R.id.menu_delete:
                showDeleteSelectedTripsDialog();
                return true;
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

    public void showDeleteSelectedTripsDialog() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(mContext);
        deleteDialog.setTitle(R.string.dialog_title_delete_trip)
                .setMessage(R.string.dialog_message_delete_trip)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete, (dialogInterface, i) -> deleteSelectedTrips())
                .show();

    }

    private void deleteSelectedTrips() {
        HashMap<Integer, DailyTrip> selectedItems = mAdapter.getSelectedItems();
        List<Integer> dailyTripIds = new ArrayList<>();
        List<DailyTrip> dailyTrips = new ArrayList<>(selectedItems.values());
        for (int i = 0; i < dailyTrips.size(); i++) {
            dailyTripIds.add(dailyTrips.get(i).getDailyTripId());
        }
        ApiClient.getApiInterface().deleteDiveShopTrips(shopUid, dailyTripIds)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, "onResponse: " + response.toString() + "\n" + response.raw().toString());
                        }
                        if (response.body() != null) {
                            if (!response.body().isError()) {
                                mAdapter.disableMultiSelectMode(true);
                                new ToastAlert(mContext)
                                        .setMessage(R.string.toast_daily_trip_deleted)
                                        .show();
                            }
                        }
                        refreshTripList(mStartDate, mEndDate, mDiveSiteId);
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new TripListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setMultiSelectListener(this);
        mAdapter.setLoadMoreListener(this);
        showFab(true);
    }

    @Override
    protected void requestData() {
        // TODO: 4/24/2017 two types of request.. diveshop trip or trips
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
    public void refresh(){
        offset = 0;
        reset = true;
        requestData();
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
        if (mAdapter.isInMultiSelectMode()) {
            mAdapter.toggleSelection(i, object);
        } else {
            TripDetailsActivity.launch(mContext, object);
        }
    }

    @Override
    public void onItemLongClick(DailyTrip object, View view, int position) {
        mAdapter.toggleSelection(position, object);
    }

    @Override
    public void onMultiSelectStateChanged(boolean enabled) {
        mContext.invalidateOptionsMenu();
        fab.setImageResource(enabled ? R.drawable.ic_delete : R.drawable.ic_add);
    }

    @Override
    public void onItemAdded(int id) {
        mContext.invalidateOptionsMenu();
    }

    @Override
    public void onItemRemoved(int id) {
        mContext.invalidateOptionsMenu();
    }

}
