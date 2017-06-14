package com.divetym.dive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.event.GuideEvent;
import com.divetym.dive.event.GuideListEvent;
import com.divetym.dive.rest.ApiClient;
import com.divetym.dive.ui.activities.AddGuideActivity;
import com.divetym.dive.ui.activities.GuideDetailsActivity;
import com.divetym.dive.ui.adapters.GuideListAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.response.GuideListResponse;
import com.divetym.dive.ui.view.ToastAlert;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private boolean sendBoatEvent;

    public static GuideListFragment getInstance(Bundle bundle) {
        GuideListFragment fragment = new GuideListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {
        AddGuideActivity.launch(mContext, null);
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new GuideListAdapter(mContext, mDataList, mRecyclerView);
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
    public void onGuideChanged(GuideEvent event) {
        Log.d(TAG, "onGuideChanged: " + event.getGuide());
        mAdapter.resetList();
        offset = 0;
        sendBoatEvent = true;
        requestData();
        EventBus.getDefault().removeStickyEvent(event);
    }
    @Override
    protected void requestData() {
        ApiClient.getApiInterface().getGuides(shopUid, offset)
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
            List<Guide> guides = response.getGuides();
            mAdapter.addData(guides);
            if(sendBoatEvent){
                sendBoatEvent = false;
                EventBus.getDefault().postSticky(new GuideListEvent(guides));
            }
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
