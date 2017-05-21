package com.divetym.dive.fragments;

import android.content.DialogInterface;
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
import com.divetym.dive.activities.DailyTripActivity;
import com.divetym.dive.activities.TripDetailsActivity;
import com.divetym.dive.adapters.TripListAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.response.DailyTripListResponse;
import com.divetym.dive.models.response.Response;
import com.divetym.dive.utils.DateUtils;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by kali_root on 4/22/2017.
 */

public class TripListFragment extends DiveTymListFragment<TripListAdapter, DailyTrip, DailyTripListResponse>
        implements BaseRecyclerAdapter.MultiSelectListener {
    public static final String TAG = TripListFragment.class.getSimpleName();
    private static final String BUNDLE_START_DATE = "bundle_start_date";
    private static final String BUNDLE_END_DATE = "bundle_end_date";
    private String startDate;
    private String endDate;
    private int mDiveSiteId;
    private boolean reset;
    private DiveSite mSelectedDiveSite;
    private Date mStartDate;
    private Date mEndDate;

    public static TripListFragment getInstance(Date startDate, Date endDate) {
        TripListFragment fragment = new TripListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_START_DATE, startDate.getTime());
        bundle.putLong(BUNDLE_END_DATE, endDate.getTime());
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface OnRefreshTripListener {
        void onDateRangedChanged(Calendar startDate, Calendar endDate);

        void onDiveSiteChanged(DiveSite diveSite);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        long startDate = args.getLong(BUNDLE_START_DATE);
        long endDate = args.getLong(BUNDLE_END_DATE);
        mDiveSiteId = -1;
        mStartDate = new Date(startDate);
        mEndDate = new Date(endDate);
        this.startDate = DateUtils.formatServerDate(mStartDate);
        this.endDate = DateUtils.formatServerDate(mEndDate);
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
        if (item.getItemId() == R.id.menu_delete) {
            showDeleteSelectedTripsDialog();
            return true;
        }
        return false;
    }

    public void showDeleteSelectedTripsDialog() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(mContext);
        deleteDialog.setTitle(R.string.dialog_title_delete_trip)
                .setMessage(R.string.dialog_message_delete_trip)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteSelectedTrips();
                    }
                })
                .show();

    }

    private void deleteSelectedTrips() {
        HashMap<Integer, DailyTrip> selectedItems = mAdapter.getSelectedItems();
        List<Integer> dailyTripIds = new ArrayList<>();
        List<DailyTrip> dailyTrips = new ArrayList<>(selectedItems.values());
        for (int i = 0; i < dailyTrips.size(); i++) {
            dailyTripIds.add(dailyTrips.get(i).getDailyTripId());
        }
        mApiService.deleteDiveShopTrips(mShopUid, dailyTripIds)
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
                        refreshTripList(mSelectedDiveSite, mStartDate, mEndDate);
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
    }

    @Override
    protected void requestData() {
        // TODO: 4/24/2017 two types of request.. diveshop trip or trips
        requestDiveShopTrips();
    }

    private void requestDiveShopTrips() {
        mApiService.getDiveShopTrips(mShopUid, mDiveSiteId, startDate, endDate, mOffset)
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

    public void refreshTripList(@Nullable DiveSite diveSite, Date startDate, Date endDate) {
        mSelectedDiveSite = diveSite;
        mStartDate = startDate;
        mEndDate = endDate;
        mOffset = 0;
        reset = true;
        if (diveSite != null) {
            mDiveSiteId = diveSite.getDiveSiteId();
        } else {
            mDiveSiteId = -1;
        }
        this.startDate = DateUtils.formatServerDate(startDate);
        this.endDate = DateUtils.formatServerDate(endDate);
        requestData();
    }

    @Override
    protected void onRequestResponse(DailyTripListResponse response) {
        if (response != null && !response.isError()) {
            Log.d(TAG,"reset: " + reset + " response: " +  response.toString());
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
        ((DailyTripActivity) mContext).mFabAdd.setImageResource(enabled ? R.drawable.ic_delete : R.drawable.ic_add);
    }

    @Override
    public void onItemAdded(int id) {
        mContext.invalidateOptionsMenu();
    }

    @Override
    public void onItemRemoved(int id) {
        mContext.invalidateOptionsMenu();
    }

    public TripListAdapter getAdapter() {
        return mAdapter;
    }
}
