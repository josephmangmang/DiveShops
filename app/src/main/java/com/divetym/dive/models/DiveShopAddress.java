package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by kali_root on 6/6/2017.
 */

public class DiveShopAddress implements Parcelable {
    public static final String EXTRA_DIVE_SHOP_ADDRESS = "com.divetym.dive.EXTRA_DIVE_SHOP_ADDRESS";
    private String fullAddress;
    private LatLng latLng;

    public DiveShopAddress() {

    }

    public DiveShopAddress(String fullAddress, LatLng latLng) {
        this.fullAddress = fullAddress;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullAddress);
        dest.writeParcelable(this.latLng, flags);
    }

    protected DiveShopAddress(Parcel in) {
        this.fullAddress = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<DiveShopAddress> CREATOR = new Creator<DiveShopAddress>() {
        @Override
        public DiveShopAddress createFromParcel(Parcel source) {
            return new DiveShopAddress(source);
        }

        @Override
        public DiveShopAddress[] newArray(int size) {
            return new DiveShopAddress[size];
        }
    };
}
