package com.divetym.dive.dialog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.divetym.dive.LocationTracker;
import com.divetym.dive.models.DiveSite;
import com.divetym.dive.models.response.DiveSiteListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kali_root on 5/11/2017.
 */

public class DiveSitesDialog extends SearchListDialog<DiveSite, DiveSiteListResponse> {
    private static final String TAG = DiveSitesDialog.class.getSimpleName();
    private double lat;
    private double lng;
    private int radius = 25;
    private LocationTracker mLocationTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationTracker = new LocationTracker(getActivity());
        if (mLocationTracker.isCanGetLocation()) {
            lat = mLocationTracker.getLatitude();
            lng = mLocationTracker.getLongitude();
        }
        Log.d(TAG, "isGpsEnabled: " + "lat: " + lat + " long: " + lng);
    }

    @Override
    public void onDestroy() {
        if (mLocationTracker != null) mLocationTracker.stopLocationService();
        super.onDestroy();
    }

    @Override
    protected void searchData(String query) {
        mApiService.getSites(query, mOffset)
                .enqueue(new Callback<DiveSiteListResponse>() {
                    @Override
                    public void onResponse(Call<DiveSiteListResponse> call, Response<DiveSiteListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DiveSiteListResponse> call, Throwable t) {
                        t.printStackTrace();
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void requestData() {
        mApiService.getSites(lat, lng, radius, mOffset)
                .enqueue(new Callback<DiveSiteListResponse>() {
                    @Override
                    public void onResponse(Call<DiveSiteListResponse> call, Response<DiveSiteListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DiveSiteListResponse> call, Throwable t) {
                        t.printStackTrace();
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void handleResponse(DiveSiteListResponse response) {
        if (response != null) {
            if (!response.isError()) {
                setDataList(response.getDivesites());
            } else {
                handleResponseError(response.getMessage());
            }
        } else {
            handleResponseError("Unknown error response is null");
        }
    }

}
