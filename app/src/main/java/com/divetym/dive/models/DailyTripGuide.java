package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.models.common.ThumbnailEntity;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripGuide extends ThumbnailEntity implements Parcelable {
    @SerializedName(ApiConstant.DAILY_TRIP_GUIDE_ID)
    private int dailyTripGuideId;
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;

    public DailyTripGuide() {
    }

    public DailyTripGuide(int dailyTripGuideId, int dailyTripId, String guideName) {
        this.dailyTripGuideId = dailyTripGuideId;
        this.dailyTripId = dailyTripId;
        this.name = guideName;
    }

    public int getDailyTripGuideId() {
        return dailyTripGuideId;
    }

    public void setDailyTripGuideId(int dailyTripGuideId) {
        this.dailyTripGuideId = dailyTripGuideId;
    }

    public int getDailyTripId() {
        return dailyTripId;
    }

    public void setDailyTripId(int dailyTripId) {
        this.dailyTripId = dailyTripId;
    }

    public String getGuideName() {
        return name;
    }

    public void setGuideName(String guideName) {
        this.name = guideName;
    }

    @Override
    public String toString() {
        return "DailyTripGuide{" +
                "dailyTripGuideId=" + dailyTripGuideId +
                ", dailyTripId=" + dailyTripId +
                ", guideName='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dailyTripGuideId);
        dest.writeInt(this.dailyTripId);
        dest.writeString(this.name);
    }

    protected DailyTripGuide(Parcel in) {
        this.dailyTripGuideId = in.readInt();
        this.dailyTripId = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<DailyTripGuide> CREATOR = new Parcelable.Creator<DailyTripGuide>() {
        @Override
        public DailyTripGuide createFromParcel(Parcel source) {
            return new DailyTripGuide(source);
        }

        @Override
        public DailyTripGuide[] newArray(int size) {
            return new DailyTripGuide[size];
        }
    };
}
