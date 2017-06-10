package com.divetym.dive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.event.DiveShopCourseEvent;
import com.divetym.dive.event.DiveShopCourseListEvent;
import com.divetym.dive.ui.activities.AddCourseActivity;
import com.divetym.dive.ui.activities.CourseDetailsActivity;
import com.divetym.dive.ui.adapters.CourseListAdapter;
import com.divetym.dive.ui.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.ui.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.models.response.DiveShopCourseListResponse;
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
 * Created by kali_root on 4/10/2017.
 */

public class CourseListFragment extends DiveTymListFragment<CourseListAdapter, DiveShopCourse, DiveShopCourseListResponse> implements
        BaseRecyclerAdapter.ItemClickListener<DiveShopCourse> {
    private static final String TAG = CourseListFragment.class.getSimpleName();
    private static final int REQUEST_ADD_COURSE = 1;
    private boolean sendBoatEvent;

    public static CourseListFragment getInstance(Bundle bundle) {
        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {
        AddCourseActivity.launch(this, null, REQUEST_ADD_COURSE);
    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new CourseListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_COURSE ) {
            // refresh the list..
            mAdapter.resetList();
            offset = 0;
            requestData();
        }
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
    public void onCourseChanged(DiveShopCourseEvent event) {
        Log.d(TAG, "onCourseChanged: " + event.getDiveShopCourse());
        mAdapter.resetList();
        offset = 0;
        sendBoatEvent = true;
        requestData();
        EventBus.getDefault().removeStickyEvent(event);
    }
    @Override
    protected void requestData() {
        mApiService.getDiveShopCourses(shopUid, offset)
                .enqueue(new Callback<DiveShopCourseListResponse>() {
                    @Override
                    public void onResponse(Call<DiveShopCourseListResponse> call, Response<DiveShopCourseListResponse> response) {
                        showProgress(false);
                        onRequestResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DiveShopCourseListResponse> call, Throwable t) {
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
    protected void onRequestResponse(DiveShopCourseListResponse response) {
        Log.d(TAG, "getDiveShopCourses onResponse: " + response.toString());
        if (response != null && !response.isError()) {
            List<DiveShopCourse> courses = response.getCourses();
            mAdapter.addData(courses);
            if (sendBoatEvent) {
                sendBoatEvent = false;
                EventBus.getDefault().postSticky(new DiveShopCourseListEvent(courses));
            }
        } else if (response != null) {
            new ToastAlert(mContext)
                    .setMessage(response.getMessage())
                    .show();
        }
    }

    @Override
    public void onItemClick(DiveShopCourse object, View view, int i) {
        Log.d(TAG, "onItemClick " + object.toString());
        CourseDetailsActivity.launch(mContext, object);
    }

    @Override
    public void onItemLongClick(DiveShopCourse object, View view, int position) {

    }

    @Override
    public void onActionClick(DiveShopCourse object, View view) {
        Log.d(TAG, "onActionClick " + object.toString());
    }
}
