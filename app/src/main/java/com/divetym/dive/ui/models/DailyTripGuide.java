package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripGuide{
    @SerializedName("daily_trip_guide_id")
    private int dailyTripGuideId;
    @SerializedName("daily_trip_id")
    private int dailyTripId;
    @SerializedName("guide_name")
    private String guideName;

    public DailyTripGuide() {
    }

    public DailyTripGuide(int dailyTripGuideId, int dailyTripId, String guideName) {
        this.dailyTripGuideId = dailyTripGuideId;
        this.dailyTripId = dailyTripId;
        this.guideName = guideName;
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
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }
}
