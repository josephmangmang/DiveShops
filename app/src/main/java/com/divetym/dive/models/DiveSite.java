package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DiveSite implements Parcelable {
    @SerializedName(ApiConstant.DIVE_SITE_ID)
    private int diveSiteId;
    @SerializedName(ApiConstant.NAME)
    private String name;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;
    @SerializedName(ApiConstant.LATITUDE)
    private double latitude;
    @SerializedName(ApiConstant.LONGITUDE)
    private double longitude;

    public DiveSite() {
    }

    public DiveSite(int diveSiteId, String name, String description, double latitude, double longitude) {
        this.diveSiteId = diveSiteId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getDiveSiteId() {
        return diveSiteId;
    }

    public void setDiveSiteId(int diveSiteId) {
        this.diveSiteId = diveSiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "DiveSite{" +
                "diveSiteId=" + diveSiteId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.diveSiteId);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected DiveSite(Parcel in) {
        this.diveSiteId = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<DiveSite> CREATOR = new Parcelable.Creator<DiveSite>() {
        @Override
        public DiveSite createFromParcel(Parcel source) {
            return new DiveSite(source);
        }

        @Override
        public DiveSite[] newArray(int size) {
            return new DiveSite[size];
        }
    };
}

