package com.divetym.dive.fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.divetym.dive.BuildConfig;
import com.divetym.dive.LocationTracker;
import com.divetym.dive.R;
import com.divetym.dive.activities.base.DiveTymActivity;
import com.divetym.dive.adapters.AddressListAddapter;
import com.divetym.dive.common.DiveShopAddress;
import com.divetym.dive.interfaces.ItemClickListener;
import com.divetym.dive.interfaces.LocationChangedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by kali_root on 6/5/2017.
 */

public class SearchMap extends MapFragment implements OnMapReadyCallback {
    private static final String TAG = SearchMap.class.getSimpleName();
    private static final int MAX_RESULTS = 5;
    private GoogleMap mMap;
    private LocationTracker mLocationTracker;
    private LatLng mSelectedLocation;
    private Geocoder mGeocoder;
    private List<Address> mAddresses;
    private LocationChangedListener mLocationChangedListener;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mLocationTracker = new LocationTracker(getActivity());
        mGeocoder = new Geocoder(getActivity(), Locale.getDefault());
        getMapAsync(this);
    }

    @Override
    public void onDestroy() {
        mLocationTracker.stopLocationService();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        resetMapCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    return;
                }
            }
            resetMapCamera();
        }
    }

    private void resetMapCamera() {
        if (mMap == null) return;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        if (mLocationTracker.isCanGetLocation()) {
            LatLng latLng = new LatLng(mLocationTracker.getLatitude(), mLocationTracker.getLongitude());
            Log.d(TAG, "LatLng: " + latLng.toString());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mSelectedLocation = latLng;
                try {
                    mAddresses = mGeocoder.getFromLocation(mSelectedLocation.latitude, mSelectedLocation.longitude, MAX_RESULTS);
                    showAddressSelectionDialog(mAddresses);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAddressSelectionDialog(List<Address> addresses) {
        if (BuildConfig.DEBUG) {
            for (Address address : addresses) {
                Log.d(TAG, "address: " + address.toString());
            }
        }
        AddressListAddapter adapter = new AddressListAddapter((DiveTymActivity) getActivity(), addresses);
        final AlertDialog listDialog = new AlertDialog.Builder(getActivity()).create();
        adapter.setItemClickListener(new ItemClickListener<Address>() {
            @Override
            public void onItemClick(Address address, View view, int i) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "address: " + address.getAddressLine(0) + " | " + address.toString());
                }
                mMap.clear();
                DiveShopAddress diveShopAddress = new DiveShopAddress(address);
                diveShopAddress.setLatLng(mSelectedLocation);
                MarkerOptions markerOption = new MarkerOptions()
                        .position(diveShopAddress.getLatLng())
                        .title(diveShopAddress.getFullAddress())
                        .draggable(true)
                        .snippet(diveShopAddress.toString());
                mMap.addMarker(markerOption);
                listDialog.dismiss();
                if (mLocationChangedListener != null) {
                    mLocationChangedListener.onLocationChanged(diveShopAddress);
                }
            }
        });
        listDialog.setTitle(R.string.dialog_title_select_address);

        RecyclerView recyclerView = (RecyclerView) getActivity().getLayoutInflater().inflate(R.layout.view_recycler_view, null, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        listDialog.setView(recyclerView);
        listDialog.show();
    }

    public void setLocationChangedListener(LocationChangedListener locationChangedListener) {
        this.mLocationChangedListener = locationChangedListener;
    }
}
