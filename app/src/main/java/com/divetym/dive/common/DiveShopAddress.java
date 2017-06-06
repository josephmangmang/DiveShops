package com.divetym.dive.common;

import android.location.Address;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

/**
 * Created by kali_root on 6/6/2017.
 */

public class DiveShopAddress extends Address {
    private String fullAddress;
    private LatLng latLng;

    public DiveShopAddress(Address address) {
        super(Locale.getDefault());
        int maxLine = address.getMaxAddressLineIndex() + 1;
        for (int i = 0; i < maxLine; i++) {
            setAddressLine(i, address.getAddressLine(i));
        }
        setAdminArea(address.getAdminArea());
        setCountryCode(address.getCountryCode());
        setCountryName(address.getCountryName());
        setExtras(address.getExtras());
        setFeatureName(address.getFeatureName());
        setLatitude(address.getLatitude());
        setLocality(address.getLocality());
        setPhone(address.getPhone());
        setLongitude(address.getLongitude());
        setPremises(address.getPremises());
        setPostalCode(address.getPostalCode());
        setSubAdminArea(address.getSubAdminArea());
        setSubLocality(address.getSubLocality());
        setSubThoroughfare(address.getSubThoroughfare());
        setThoroughfare(address.getThoroughfare());
        setUrl(address.getUrl());
        latLng = new LatLng(getLatitude(), getLongitude());

    }

    public DiveShopAddress(Locale locale) {
        super(locale);
        latLng = new LatLng(getLatitude(), getLongitude());
    }

    public String getFullAddress() {
        if (fullAddress == null) {
            int maxIndex = getMaxAddressLineIndex() + 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < maxIndex; i++) {
                builder.append(getAddressLine(i));
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            fullAddress = builder.toString();
        }
        return fullAddress;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
