package com.divetym.dive.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.divetym.dive.R;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.AddressListAddapter;
import com.divetym.dive.models.DiveShopAddress;
import com.divetym.dive.interfaces.ItemClickListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by kali_root on 6/6/2017.
 */

public class SearchMapActivity extends DiveTymActivity implements OnMapReadyCallback, PlaceSelectionListener {
    private static final int MAX_RESULTS = 5;
    private static final String TAG = SearchMapActivity.class.getSimpleName();
    private static final float DEFAULT_ZOOM = 13.0f;
    private GoogleMap mMap;
    private LatLng mSelectedLocation;
    private Geocoder mGeocoder;
    private DiveShopAddress mDiveShopAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        getSupportActionBar().hide();
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragment_autocomplete);
        mGeocoder = new Geocoder(this, Locale.getDefault());

        if (getIntent() != null) {
            mDiveShopAddress = getIntent().getParcelableExtra(DiveShopAddress.EXTRA_DIVE_SHOP_ADDRESS);
        }
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (autocompleteFragment != null) {
            autocompleteFragment.setOnPlaceSelectedListener(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mDiveShopAddress.getFullAddress() != null) {
            MarkerOptions markerOption = new MarkerOptions()
                    .position(mDiveShopAddress.getLatLng())
                    .title(mDiveShopAddress.getFullAddress())
                    .draggable(true);
            mMap.addMarker(markerOption);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mDiveShopAddress.getLatLng(), DEFAULT_ZOOM));
        }
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mSelectedLocation = latLng;
                try {
                    showAddressSelectionDialog(mGeocoder.getFromLocation(mSelectedLocation.latitude, mSelectedLocation.longitude, MAX_RESULTS));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAddressSelectionDialog(List<Address> addresses) {
        AddressListAddapter adapter = new AddressListAddapter(this, addresses);
        final AlertDialog listDialog = new AlertDialog.Builder(this).create();
        adapter.setItemClickListener(new ItemClickListener<DiveShopAddress>() {
            @Override
            public void onItemClick(DiveShopAddress diveShopAddress, View view, int i) {
                mMap.clear();
                diveShopAddress.setLatLng(mSelectedLocation);
                MarkerOptions markerOption = new MarkerOptions()
                        .position(diveShopAddress.getLatLng())
                        .title(diveShopAddress.getFullAddress())
                        .draggable(true);
                mMap.addMarker(markerOption);
                listDialog.dismiss();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(diveShopAddress.getLatLng(), DEFAULT_ZOOM));
                Intent resultIntent = new Intent();
                resultIntent.putExtra(DiveShopAddress.EXTRA_DIVE_SHOP_ADDRESS, diveShopAddress);
                setResult(RESULT_OK, resultIntent);
            }
        });
        listDialog.setTitle(R.string.dialog_title_select_address);

        RecyclerView recyclerView = (RecyclerView) getLayoutInflater().inflate(R.layout.view_recycler_view, null, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        listDialog.setView(recyclerView);
        listDialog.show();
    }

    @Override
    public void onPlaceSelected(Place place) {
        if (mMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
        }
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: " + status.getStatusMessage());
    }
}
