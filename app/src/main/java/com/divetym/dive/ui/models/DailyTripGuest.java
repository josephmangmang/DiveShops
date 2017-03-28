package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripGuest extends Diver{
    @SerializedName("daily_trip_guest_id")
    private int dailyTripGuestId;
    @SerializedName("daily_trip_id")
    private int dailyTripId;

    public DailyTripGuest() {
    }

    public DailyTripGuest(int userId, String email, int dailyTripGuestId, int dailyTripId) {
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
}
