package com.divetym.dive.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.divetym.dive.GlideApp;
import com.divetym.dive.R;
import com.divetym.dive.models.DiveShop;
import com.divetym.dive.ui.activities.diver.DiverDiveShopActivity;
import com.divetym.dive.ui.view.RobotoTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;

/**
 * Created by kali_root on 6/20/2017.
 */

public class DiveShopsMapFragment extends SupportMapFragment implements OnMapReadyCallback {

    public static final String TAG = DiveShopsMapFragment.class.getSimpleName();
    private GoogleMap mMap;
    private List<DiveShop> diveShops;
    private HashMap<String, DiveShop> mMarkers = new HashMap<>();


    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            DiveShop shop = mMarkers.get(marker.getId());
            DiverDiveShopActivity.launch(getActivity(), shop.getDiveShopUid());
        }
    };
    private GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            DiveShop shop = mMarkers.get(marker.getId());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_marker_info, null);
            RobotoTextView name = (RobotoTextView) view.findViewById(R.id.text_name);
            RatingBar rating = (RatingBar) view.findViewById(R.id.rating);
            RobotoTextView ratingMark = (RobotoTextView) view.findViewById(R.id.text_rating_mark);
            RobotoTextView price = (RobotoTextView) view.findViewById(R.id.text_price);
            RobotoTextView currency = (RobotoTextView) view.findViewById(R.id.text_price_currency);
            name.setText(shop.getName());
            rating.setRating(10);// TODO: 6/19/2017 implement rating
            ratingMark.setText("Excellent 10");// TODO: 6/19/2017  mplement rating remarks
            price.setText(shop.getPricePerDive().toString());
            currency.setText("PHP"); // TODO: 6/19/2017 Currency
            return view;
        }
    };

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getMapAsync(this);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        mMap = googleMap;
        if (mMap != null) {
            mMap.setOnInfoWindowClickListener(infoWindowClickListener);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.setInfoWindowAdapter(infoWindowAdapter);
            addMarkers();
        }
    }

    public void setList(List<DiveShop> diveShops) {
        this.diveShops = diveShops;
    }

    public void addMarkers() {
        if (diveShops != null && diveShops.size() > 0) {
            if (mMap != null) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (int i = 0; i < diveShops.size(); i++) {
                    DiveShop diveShop = diveShops.get(i);
                    LatLng latlang = new LatLng(diveShop.getLatitiude(), diveShop.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(latlang)
                            .title(diveShop.getName());
                    Marker marker = mMap.addMarker(markerOptions);
                    mMarkers.put(marker.getId(), diveShop);
                    builder.include(marker.getPosition());
                }
                int padding = getResources().getDimensionPixelSize(R.dimen.margin_huge);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), padding));
            }
        }
    }

}
