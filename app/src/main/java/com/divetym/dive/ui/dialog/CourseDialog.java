package com.divetym.dive.ui.dialog;

import com.divetym.dive.common.SortOption;
import com.divetym.dive.models.Course;
import com.divetym.dive.models.response.CourseListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.divetym.dive.common.SortOption.*;

/**
 * Created by kali_root on 5/25/2017.
 */

public class CourseDialog extends SearchListDialog<Course, CourseListResponse> {

    private SortOption mSortOption = new SortOption(Order.name, Sort.ASC);

    @Override
    protected void searchData(String query) {
        mApiService.getCourses(query, offset, mSortOption.getSort().name(), mSortOption.getOrder().name())
                .enqueue(new Callback<CourseListResponse>() {
                    @Override
                    public void onResponse(Call<CourseListResponse> call, Response<CourseListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<CourseListResponse> call, Throwable t) {
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void requestData() {
        mApiService.getCourses(offset, mSortOption.getSort().name(), mSortOption.getOrder().name())
                .enqueue(new Callback<CourseListResponse>() {
                    @Override
                    public void onResponse(Call<CourseListResponse> call, Response<CourseListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<CourseListResponse> call, Throwable t) {
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void handleResponse(CourseListResponse response) {
        if (response != null) {
            if (!response.isError()) {
                setDataList(response.getCourses());
            } else {
                handleResponseError(response.getMessage());
            }
        } else {
            handleResponseError("Unknown error response is null");
        }
    }
}
