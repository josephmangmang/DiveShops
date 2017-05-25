package com.divetym.dive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.adapters.GuideListAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.User;
import com.divetym.dive.models.response.GuideListResponse;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 5/24/2017.
 */

public class GuideListFragment extends DiveTymListFragment<GuideListAdapter, Guide, GuideListResponse> {

    private static final String TAG = GuideListFragment.class.getSimpleName();

    public static GuideListFragment getInstance(Bundle bundle) {
        GuideListFragment fragment = new GuideListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {

    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new GuideListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    @Override
    protected void requestData() {
        mApiService.getGuides(mShopUid, mOffset)
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
}
