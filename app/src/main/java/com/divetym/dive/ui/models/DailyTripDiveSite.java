package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripDiveSite extends DiveSite{
    @SerializedName("daily_trip_dive_site")
    private int dailyTripDiveSite;
    @SerializedName("daily_trip_id")
    private int dailyTripId;

    public DailyTripDiveSite() {
    }

    public DailyTripDiveSite(int diveSiteId, String name, String description, double latitude, double longitude, int dailyTripDiveSite, int dailyTripId) {
        super(diveSiteId, name, description, latitude, longitude);
        this.dailyTripDiveSite = dailyTripDiveSite;
        this.dailyTripId = dailyTripId;
    }

    public int getDailyTripDiveSite() {
        return dailyTripDiveSite;
    }

    public void setDailyTripDiveSite(int dailyTripDiveSite) {
        this.dailyTripDiveSite = dailyTripDiveSite;
    }

    public int getDailyTripId() {
        return dailyTripId;
    }

    public void setDailyTripId(int dailyTripId) {
        this.dailyTripId = dailyTripId;
    }
}
