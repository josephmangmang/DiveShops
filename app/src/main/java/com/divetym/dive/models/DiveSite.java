package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DiveSite extends ThumbnailEntity implements Parcelable {
    @SerializedName(ApiConstant.DIVE_SITE_ID)
    private int diveSiteId;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;
    @SerializedName(ApiConstant.LATITUDE)
    private double latitude;
    @SerializedName(ApiConstant.LONGITUDE)
    private double longitude;

    public int getDiveSiteId() {
        return diveSiteId;
    }

    public void setDiveSiteId(int diveSiteId) {
        this.diveSiteId = diveSiteId;
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

    public DiveSite() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.diveSiteId);
        dest.writeString(this.description);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected DiveSite(Parcel in) {
        super(in);
        this.diveSiteId = in.readInt();
        this.description = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Creator<DiveSite> CREATOR = new Creator<DiveSite>() {
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

