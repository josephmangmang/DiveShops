package com.divetym.dive.dialog;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        } else if (isNetworkEnabled) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        }
        Log.d(TAG, "isGpsEnabled: " + isGPSEnabled + " isNetworkEnabled: " + isNetworkEnabled + "lat: " + lat + " long: " + lng);
    }

    @Override
    protected void onSearchClicked(String query) {
        mLastRequestMethod = REQUEST_METHOD_SEARCH;
        mOffset = 0;
        searchData(query);
    }

    protected void searchData(String query) {
        mApiService.getSites(query, mOffset)
                .enqueue(new Callback<DiveSiteListResponse>() {
                    @Override
                    public void onResponse(Call<DiveSiteListResponse> call, Response<DiveSiteListResponse> response) {
                        handleResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<DiveSiteListResponse> call, Throwable t) {
                        handleResponseError(t.getMessage());
                    }
                });
    }

    @Override
    protected void requestData() {
        if (mLastRequestMethod == REQUEST_METHOD_SEARCH) {
            searchData(mLastSearchQuery);
        } else {
            mApiService.getSites(lat, lng, radius, mOffset)
                    .enqueue(new Callback<DiveSiteListResponse>() {
                        @Override
                        public void onResponse(Call<DiveSiteListResponse> call, Response<DiveSiteListResponse> response) {
                            handleResponse(response.body());
                        }

                        @Override
                        public void onFailure(Call<DiveSiteListResponse> call, Throwable t) {
                            handleResponseError(t.getMessage());
                        }
                    });
        }
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
