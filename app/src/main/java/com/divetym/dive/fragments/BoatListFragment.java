package com.divetym.dive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.activities.BoatDetailsActivity;
import com.divetym.dive.adapters.BoatListAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.response.BoatListResponse;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/15/2017.
 */

public class BoatListFragment extends DiveTymListFragment<BoatListAdapter, Boat, BoatListResponse> implements BaseRecyclerAdapter.ItemClickListener<Boat> {

    private static final String TAG = BoatListFragment.class.getSimpleName();

    public static BoatListFragment getInstance(@Nullable ArrayList<Boat> boats) {
        BoatListFragment fragment = new BoatListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(EXTRA_LIST, boats);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new BoatListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    @Override
    protected void requestData() {
        mApiService.getDiveShopBoats(mShopUid, mOffset)
                .enqueue(new Callback<BoatListResponse>() {
                    @Override
                    public void onResponse(Call<BoatListResponse> call, Response<BoatListResponse> response) {
                        showProgress(false);
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<BoatListResponse> call, Throwable t) {
                        showProgress(false);
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, call.request().toString());
                            t.printStackTrace();
                        }
                        Log.e(TAG, "requestData onFailiure: " + t.getMessage());
                    }
                });
    }

    @Override
    protected void onRequestResponse(BoatListResponse response) {
        if (response != null && !response.isError()) {
            List<Boat> boats = response.getBoats();
            mAdapter.addData(boats);
        } else if (response != null) {
            ToastAlert.makeText(mContext, response.getMessage(), ToastAlert.LENGTH_SHORT);
        }
        setEmpty(mDataList.size() == 0);
    }


    @Override
    public void onItemClick(Boat object, View view) {
        Log.d(TAG, "onItemClick " + object.toString());
        BoatDetailsActivity.launch(mContext, object);
    }

    @Override
    public void onActionClick(Boat object, View view) {

    }
}
