package com.divetym.dive.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.activities.AddBoatActivity;
import com.divetym.dive.activities.BoatDetailsActivity;
import com.divetym.dive.adapters.BoatListAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.response.BoatListResponse;
import com.divetym.dive.view.ToastAlert;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kali_root on 4/15/2017.
 */

public class BoatListFragment extends DiveTymListFragment<BoatListAdapter, Boat, BoatListResponse> {

    private static final String TAG = BoatListFragment.class.getSimpleName();
    private static final int REQUEST_ADD_BOAT = 1;

    public static BoatListFragment getInstance(Bundle bundle) {
        BoatListFragment fragment = new BoatListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {
        AddBoatActivity.launch(this, null,REQUEST_ADD_BOAT );
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new BoatListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_BOAT ) {
            // refresh the list..
            mAdapter.resetList();
            offset = 0;
            requestData();
        }
    }
    @Override
    protected void requestData() {
        mApiService.getDiveShopBoats(shopUid, offset)
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
                        Log.e(TAG, "getData onFailiure: " + t.getMessage());
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
    public void onItemClick(Boat object, View view, int position) {
        Log.d(TAG, "onItemClick " + object.toString());
        BoatDetailsActivity.launch(mContext, object);
    }

}
