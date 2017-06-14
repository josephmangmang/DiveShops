package com.divetym.dive.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.event.BoatListEvent;
import com.divetym.dive.event.BoatEvent;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.AddBoatActivity;
import com.divetym.dive.ui.activities.BoatDetailsActivity;
import com.divetym.dive.ui.adapters.BoatListAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.response.BoatListResponse;
import com.divetym.dive.ui.view.ToastAlert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/15/2017.
 */

public class BoatListFragment extends DiveTymListFragment<BoatListAdapter, Boat, BoatListResponse> {

    private static final String TAG = BoatListFragment.class.getSimpleName();
    private static final int REQUEST_ADD_BOAT = 1;
    private boolean sendBoatEvent;

    public static BoatListFragment getInstance(Bundle bundle) {
        BoatListFragment fragment = new BoatListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {
        AddBoatActivity.launch(mContext, null);
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new BoatListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBoatChanged(BoatEvent event) {
        Log.d(TAG, "onBoatChanged: " + event.getBoat());
        mAdapter.resetList();
        offset = 0;
        sendBoatEvent = true;
        requestData();
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void requestData() {
        ApiClient.getApiInterface().getDiveShopBoats(shopUid, offset)
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
            if (sendBoatEvent) {
                sendBoatEvent = false;
                EventBus.getDefault().postSticky(new BoatListEvent(boats));
            }
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
