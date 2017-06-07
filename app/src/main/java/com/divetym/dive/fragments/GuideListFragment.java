package com.divetym.dive.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.activities.AddGuideActivity;
import com.divetym.dive.activities.GuideDetailsActivity;
import com.divetym.dive.adapters.GuideListAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.response.GuideListResponse;
import com.divetym.dive.view.ToastAlert;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kali_root on 5/24/2017.
 */

public class GuideListFragment extends DiveTymListFragment<GuideListAdapter, Guide, GuideListResponse> {

    private static final String TAG = GuideListFragment.class.getSimpleName();
    private static final int REQUEST_ADD_GUIDE = 1;

    public static GuideListFragment getInstance(Bundle bundle) {
        GuideListFragment fragment = new GuideListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {
        AddGuideActivity.launch(this, null, REQUEST_ADD_GUIDE);
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new GuideListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_GUIDE) {
            // refresh the list..
            mAdapter.resetList();
            offset = 0;
            requestData();
        }
    }

    @Override
    protected void requestData() {
        mApiService.getGuides(shopUid, offset)
                .enqueue(new Callback<GuideListResponse>() {
                    @Override
                    public void onResponse(Call<GuideListResponse> call, Response<GuideListResponse> response) {
                        showProgress(false);
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<GuideListResponse> call, Throwable t) {
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
    protected void onRequestResponse(GuideListResponse response) {
        if (response != null && !response.isError()) {
            List<Guide> boats = response.getGuides();
            mAdapter.addData(boats);
        } else if (response != null) {
            ToastAlert.makeText(mContext, response.getMessage(), ToastAlert.LENGTH_SHORT);
        }
        setEmpty(mDataList.size() == 0);
    }

    @Override
    public void onItemClick(Guide object, View view, int i) {
        GuideDetailsActivity.launch(mContext, object);
    }
}
