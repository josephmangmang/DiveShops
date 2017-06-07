package com.divetym.dive.fragments;

import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.R;
import com.divetym.dive.activities.DailyTripActivity;
import com.divetym.dive.adapters.SearchListAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.response.DiveSiteListResponse;
import com.divetym.dive.view.ToastAlert;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.sahildave.widget.SearchViewLayout;

import static com.divetym.dive.fragments.TripListFragment.*;

/**
 * Created by kali_root on 4/21/2017.
 */

public class SearchListFragment extends DiveTymListFragment<SearchListAdapter, DiveSite, DiveSiteListResponse>
        implements BaseRecyclerAdapter.ItemClickListener<DiveSite>, SearchViewLayout.SearchListener {

    private static final String TAG = SearchListFragment.class.getSimpleName();
    private String searchName = "";
    private boolean searchRequest;
    private OnRefreshTripListener mOnRefreshTripListener;

    public SearchListFragment() {
    }

    @Override
    protected void onFabClicked() {

    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new SearchListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
        mOnRefreshTripListener = (DailyTripActivity) mContext;
        setEmptyTextMessage(getString(R.string.message_empty_search));
    }

    @Override
    protected void requestData() {
        mApiService.getSites(searchName, offset)
                .enqueue(new Callback<DiveSiteListResponse>() {
                    @Override
                    public void onResponse(Call<DiveSiteListResponse> call, Response<DiveSiteListResponse> response) {
                        showProgress(false);
                        Log.d(TAG, "onResponse: " + response.toString());
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DiveSiteListResponse> call, Throwable t) {
                        showProgress(false);
                        Log.e(TAG, "getDiveShopCourses onFailiure: " + t.getMessage());
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onRequestResponse(DiveSiteListResponse response) {
        if (response != null && !response.isError()) {
            Log.d(TAG, response.toString());
            if (searchRequest) {
                mAdapter.replaceData(response.getDivesites());
                searchRequest = false;
            } else {
                mAdapter.addData(response.getDivesites());
            }
        } else if (response != null) {
            ToastAlert.makeText(mContext, response.getMessage(), ToastAlert.LENGTH_SHORT);
        }
        setEmpty(mDataList.size() == 0);
    }

    @Override
    public void onFinished(String searchKeyword) {
        searchName = searchKeyword;
        Log.d(TAG, "onFinised search : " + searchKeyword);
        requestDiveSiteByName();
    }

    private void requestDiveSiteByName() {
        offset = 0;
        searchRequest = true;
        requestData();
    }

    @Override
    public void onItemClick(DiveSite object, View view, int i) {
        if (mOnRefreshTripListener != null) {
            mOnRefreshTripListener.onDiveSiteChanged(object);
        }
    }

    @Override
    public void onItemLongClick(DiveSite object, View view, int position) {

    }
}
