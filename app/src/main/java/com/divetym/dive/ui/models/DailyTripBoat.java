package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DailyTripBoat extends Boat{
    @SerializedName(ApiConstant.DAILY_TRIP_BOAT_ID)
    private int dailyTripBoatId;
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;

    public DailyTripBoat() {
    }

    public DailyTripBoat(int boatId, int diveShopUid, String name, String imageUrl, int dailyTripBoatId, int dailyTripId) {
        super(boatId, diveShopUid, name, imageUrl);
        this.dailyTripBoatId = dailyTripBoatId;
        this.dailyTripId = dailyTripId;
    }

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
}
