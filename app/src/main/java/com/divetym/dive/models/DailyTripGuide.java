package com.divetym.dive.models;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DailyTripGuide{
    @SerializedName(ApiConstant.DAILY_TRIP_GUIDE_ID)
    private int dailyTripGuideId;
    @SerializedName(ApiConstant.DAILY_TRIP_ID)
    private int dailyTripId;
    @SerializedName(ApiConstant.GUIDE_NAME)
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

    @Override
    public String toString() {
        return "DailyTripGuide{" +
                "dailyTripGuideId=" + dailyTripGuideId +
                ", dailyTripId=" + dailyTripId +
                ", guideName='" + guideName + '\'' +
                '}';
    }
}
