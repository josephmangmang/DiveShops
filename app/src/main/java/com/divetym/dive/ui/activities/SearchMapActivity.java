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
import com.divetym.dive.event.LocationEvent;
import com.divetym.dive.ui.activities.base.DiveTymActivity;
import com.divetym.dive.ui.adapters.AddressListAddapter;
import com.divetym.dive.models.LocationAddress;
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

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by kali_root on 6/6/2017.
 */

public class SearchMapActivity extends DiveTymActivity implements OnMapReadyCallback, PlaceSelectionListener {
    public static final String EXTRA_MAX_RESULTS = "com.divetym.time.SearchMapActivity.EXTRA_MAX_RESULTS";
    private static int MAX_RESULTS = 5;
    private static final String TAG = SearchMapActivity.class.getSimpleName();
    private static final float DEFAULT_ZOOM = 13.0f;
    private GoogleMap mMap;
    private LatLng mSelectedLocation;
    private Geocoder mGeocoder;
    private LocationAddress mLocationAddress;

    public static void launch(DiveTymActivity context, int maxResults) {
        Intent intent = new Intent(context, SearchMapActivity.class);
        intent.putExtra(EXTRA_MAX_RESULTS, maxResults);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
        getSupportActionBar().hide();
        MAX_RESULTS = getIntent().getIntExtra(EXTRA_MAX_RESULTS, MAX_RESULTS);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.fragment_autocomplete);
        mGeocoder = new Geocoder(this, Locale.getDefault());

        if (getIntent() != null) {
            mLocationAddress = getIntent().getParcelableExtra(LocationAddress.EXTRA_DIVE_SHOP_ADDRESS);
        }
        if (mLocationAddress == null) {
            mLocationAddress = new LocationAddress();
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
        if (mLocationAddress != null && mLocationAddress.getFullAddress() != null) {
            MarkerOptions markerOption = new MarkerOptions()
                    .position(mLocationAddress.getLatLng())
                    .title(mLocationAddress.getFullAddress())
                    .draggable(true);
            mMap.addMarker(markerOption);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLocationAddress.getLatLng(), DEFAULT_ZOOM));
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
        mMap.setOnMapClickListener(latLng -> {
            mSelectedLocation = latLng;
            try {
                if (MAX_RESULTS == 1) {
                    mMap.clear();
                    List<Address> fromLocation = mGeocoder.getFromLocation(mSelectedLocation.latitude, mSelectedLocation.longitude, 1);
                    if (fromLocation.size() == 0) {
                        return;
                    }
                    Address address = fromLocation.get(0);
                    int addressIndex = address.getMaxAddressLineIndex() + 1;
                    StringBuilder fulladdress = new StringBuilder();
                    for (int i = 0; i < addressIndex; i++) {
                        if (address.getAddressLine(i).equals("Unnamed Road")) {
                            continue;
                        }
                        fulladdress.append(address.getAddressLine(i));
                        fulladdress.append(", ");
                    }
                    fulladdress.delete(fulladdress.length() - 2, fulladdress.length());
                    mLocationAddress.setFullAddress(fulladdress.toString());
                    mLocationAddress.setLatLng(latLng);
                    MarkerOptions markerOption = new MarkerOptions()
                            .position(mLocationAddress.getLatLng())
                            .title(mLocationAddress.getFullAddress());
                    mMap.addMarker(markerOption);
                    notifyLocationChanged();
                } else {
                    showAddressSelectionDialog(mGeocoder.getFromLocation(mSelectedLocation.latitude, mSelectedLocation.longitude, MAX_RESULTS));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void showAddressSelectionDialog(List<Address> addresses) {
        AddressListAddapter adapter = new AddressListAddapter(this, addresses);
        final AlertDialog listDialog = new AlertDialog.Builder(this).create();
        adapter.setItemClickListener(new ItemClickListener<LocationAddress>() {
            @Override
            public void onItemClick(LocationAddress diveShopAddress, View view, int i) {
                mMap.clear();
                diveShopAddress.setLatLng(mSelectedLocation);
                MarkerOptions markerOption = new MarkerOptions()
                        .position(diveShopAddress.getLatLng())
                        .title(diveShopAddress.getFullAddress());
                mMap.addMarker(markerOption);
                listDialog.dismiss();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(diveShopAddress.getLatLng(), DEFAULT_ZOOM));
                Intent resultIntent = new Intent();
                resultIntent.putExtra(LocationAddress.EXTRA_DIVE_SHOP_ADDRESS, diveShopAddress);
                setResult(RESULT_OK, resultIntent);
                notifyLocationChanged();
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
            if (MAX_RESULTS == 1) {
                mMap.clear();
                mLocationAddress.setFullAddress((String) place.getAddress());
                mLocationAddress.setLatLng(place.getLatLng());
                MarkerOptions markerOption = new MarkerOptions()
                        .position(mLocationAddress.getLatLng())
                        .title(mLocationAddress.getFullAddress())
                        .draggable(true);
                mMap.addMarker(markerOption);
                notifyLocationChanged();
            }
        }
    }

    private void notifyLocationChanged() {
        EventBus.getDefault().postSticky(new LocationEvent(mLocationAddress));
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: " + status.getStatusMessage());
    }
}
