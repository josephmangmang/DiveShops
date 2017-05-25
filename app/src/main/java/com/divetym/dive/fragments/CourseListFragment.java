package com.divetym.dive.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.activities.CourseDetailsActivity;
import com.divetym.dive.adapters.CourseListAdapter;
import com.divetym.dive.adapters.base.BaseRecyclerAdapter;
import com.divetym.dive.fragments.base.DiveTymListFragment;
import com.divetym.dive.models.DiveShopCourse;
import com.divetym.dive.models.response.DiveShopCourseListResponse;
import com.divetym.dive.view.ToastAlert;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 4/10/2017.
 */

public class CourseListFragment extends DiveTymListFragment<CourseListAdapter, DiveShopCourse, DiveShopCourseListResponse> implements
        BaseRecyclerAdapter.ItemClickListener<DiveShopCourse> {
    private static final String TAG = CourseListFragment.class.getSimpleName();

    public static CourseListFragment getInstance(Bundle bundle) {
        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onFabClicked() {

    }

    @Override
    protected void initializeAdapter() {
        mAdapter = new CourseListAdapter(mContext, mDataList, mRecyclerView);
        mAdapter.setItemClickListener(this);
        mAdapter.setLoadMoreListener(this);
    }

    @Override
    protected void requestData() {
        mApiService.getDiveShopCourses(mShopUid, mOffset)
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
