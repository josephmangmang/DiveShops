package com.divetym.dive.models;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripGuest extends Diver{
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
}
