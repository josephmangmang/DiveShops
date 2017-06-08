package com.divetym.dive.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.divetym.dive.common.SessionManager;
import com.divetym.dive.models.Boat;
import com.divetym.dive.models.response.BoatListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 5/15/2017.
 */

public class BoatsDialog extends SearchListDialog<Boat, BoatListResponse> {

    private String mDiveShopUid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDiveShopUid = SessionManager.getInstance(getActivity()).getDiveShopUid();
    }

    @Override
    protected void searchData(String query) {
        mApiService.getDiveShopBoats(mDiveShopUid, offset, query)
                .enqueue(new Callback<BoatListResponse>() {
                    @Override
                    public void onResponse(Call<BoatListResponse> call, Response<BoatListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<BoatListResponse> call, Throwable t) {
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void requestData() {
        mApiService.getDiveShopBoats(mDiveShopUid, offset)
                .enqueue(new Callback<BoatListResponse>() {
                    @Override
                    public void onResponse(Call<BoatListResponse> call, Response<BoatListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<BoatListResponse> call, Throwable t) {
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void handleResponse(BoatListResponse response) {
        if (response != null) {
            if (!response.isError()) {
                setDataList(response.getBoats());
            } else {
                handleResponseError(response.getMessage());
            }
        } else {
            handleResponseError("Unknown error response is null");
        }
    }
}
