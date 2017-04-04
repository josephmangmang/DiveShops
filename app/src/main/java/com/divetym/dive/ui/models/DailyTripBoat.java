package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DailyTripBoat extends Boat{
    @SerializedName("daily_trip_boat")
    private int dailyTripBoat;
    @SerializedName("daily_trip_id")
    private int dailyTripId;

    public DailyTripBoat() {
    }

    public DailyTripBoat(int boatId, int diveShopUid, String name, String imageUrl, int dailyTripBoat, int dailyTripId) {
        super(boatId, diveShopUid, name, imageUrl);
        this.dailyTripBoat = dailyTripBoat;
        this.dailyTripId = dailyTripId;
    }

    public int getDailyTripBoat() {
        return dailyTripBoat;
    }

    public void setDailyTripBoat(int dailyTripBoat) {
        this.dailyTripBoat = dailyTripBoat;
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
                "dailyTripBoat=" + dailyTripBoat +
                ", dailyTripId=" + dailyTripId +
                '}';
    }
}
