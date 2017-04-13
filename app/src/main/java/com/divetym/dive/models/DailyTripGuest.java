package com.divetym.dive.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripGuest extends Diver implements Parcelable {
    @SerializedName(ApiConstant.DAILY_TRIP_GUEST_ID)
    private int dailyTripGuestId;
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;

    public DailyTripGuest() {
    }

    public DailyTripGuest(String userId, String email, int dailyTripGuestId, int dailyTripId) {
        super(userId, email);
        this.dailyTripGuestId = dailyTripGuestId;
        this.dailyTripId = dailyTripId;
    }

    public int getDailyTripGuestId() {
        return dailyTripGuestId;
    }

    public void setDailyTripGuestId(int dailyTripGuestId) {
        this.dailyTripGuestId = dailyTripGuestId;
    }

    public int getDailyTripId() {
        return dailyTripId;
    }

    public void setDailyTripId(int dailyTripId) {
        this.dailyTripId = dailyTripId;
    }

    @Override
    public String toString() {
        return "DailyTripGuest{" +
                "dailyTripGuestId=" + dailyTripGuestId +
                ", dailyTripId=" + dailyTripId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dailyTripGuestId);
        dest.writeInt(this.dailyTripId);
    }

    protected DailyTripGuest(Parcel in) {
        this.dailyTripGuestId = in.readInt();
        this.dailyTripId = in.readInt();
    }

    public static final Parcelable.Creator<DailyTripGuest> CREATOR = new Parcelable.Creator<DailyTripGuest>() {
        @Override
        public DailyTripGuest createFromParcel(Parcel source) {
            return new DailyTripGuest(source);
        }

        @Override
        public DailyTripGuest[] newArray(int size) {
            return new DailyTripGuest[size];
        }
    };
}
