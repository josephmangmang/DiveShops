package com.divetym.dive.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.common.SessionManager;
import com.divetym.dive.models.Guide;
import com.divetym.dive.models.response.GuideListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 5/16/2017.
 */
public class GuidesDialog extends SearchListDialog<Guide, GuideListResponse> {

    private String mDiveShopUid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiveShopUid = SessionManager.getInstance(getActivity()).getDiveShopUid();
    }

    @Override
    protected void searchData(String query) {
        mApiService.getGuides(mDiveShopUid, query, offset)
                .enqueue(new Callback<GuideListResponse>() {
                    @Override
                    public void onResponse(Call<GuideListResponse> call, Response<GuideListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<GuideListResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace();
                        }
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void requestData() {
        mApiService.getGuides(mDiveShopUid, offset)
                .enqueue(new Callback<GuideListResponse>() {
                    @Override
                    public void onResponse(Call<GuideListResponse> call, Response<GuideListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<GuideListResponse> call, Throwable t) {
                        if (BuildConfig.DEBUG) {
                            t.printStackTrace();
                        }
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void handleResponse(GuideListResponse response) {
        if (response != null) {
            if (!response.isError()) {
                setDataList(response.getGuides());
            } else {
                handleResponseError(response.getMessage());
            }
        } else {
            handleResponseError("Unknown error response is null");
        }
    }
}
