package com.divetym.dive.models;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripDiveSite extends DiveSite{
    @SerializedName(ApiConstant.DAILY_TRIP_DIVE_SITE_ID)
    private int dailyTripDiveSiteId;
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;

    public DailyTripDiveSite() {
    }

    public DailyTripDiveSite(int diveSiteId, String name, String description, double latitude, double longitude, int dailyTripDiveSiteId, int dailyTripId) {
        super(diveSiteId, name, description, latitude, longitude);
        this.dailyTripDiveSiteId = dailyTripDiveSiteId;
        this.dailyTripId = dailyTripId;
    }

    public int getDailyTripDiveSiteId() {
        return dailyTripDiveSiteId;
    }

    public void setDailyTripDiveSiteId(int dailyTripDiveSiteId) {
        this.dailyTripDiveSiteId = dailyTripDiveSiteId;
    }

    public int getDailyTripId() {
        return dailyTripId;
    }

    public void setDailyTripId(int dailyTripId) {
        this.dailyTripId = dailyTripId;
    }

    @Override
    public String toString() {
        return "DailyTripDiveSite{" +
                "dailyTripDiveSiteId=" + dailyTripDiveSiteId +
                ", dailyTripId=" + dailyTripId +
                '}';
    }
}
