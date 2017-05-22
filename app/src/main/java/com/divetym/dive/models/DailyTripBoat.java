package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DailyTripBoat extends Boat implements Parcelable{
    @SerializedName(ApiConstant.DAILY_TRIP_BOAT_ID)
    private int dailyTripBoatId;
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;


    public int getDailyTripBoatId() {
        return dailyTripBoatId;
    }

    public void setDailyTripBoatId(int dailyTripBoatId) {
        this.dailyTripBoatId = dailyTripBoatId;
    }

    public int getDailyTripId() {
        return dailyTripId;
    }

    public void setDailyTripId(int dailyTripId) {
        this.dailyTripId = dailyTripId;
    }

    @Override
    public String toString() {
        return "DailyTripBoat{" +
                "dailyTripBoatId=" + dailyTripBoatId +
                ", dailyTripId=" + dailyTripId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.dailyTripBoatId);
        dest.writeInt(this.dailyTripId);
    }

    public DailyTripBoat() {
    }

    protected DailyTripBoat(Parcel in) {
        super(in);
        this.dailyTripBoatId = in.readInt();
        this.dailyTripId = in.readInt();
    }

    public static final Creator<DailyTripBoat> CREATOR = new Creator<DailyTripBoat>() {
        @Override
        public DailyTripBoat createFromParcel(Parcel source) {
            return new DailyTripBoat(source);
        }

        @Override
        public DailyTripBoat[] newArray(int size) {
            return new DailyTripBoat[size];
        }
    };
}
