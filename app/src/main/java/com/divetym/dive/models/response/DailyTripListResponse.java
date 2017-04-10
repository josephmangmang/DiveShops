package com.divetym.dive.models.response;

import com.divetym.dive.models.DailyTrip;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DailyTripListResponse extends Response {
    @SerializedName(ApiConstant.DAILY_TRIPS)
    private List<DailyTrip> dailyTrips;

    public List<DailyTrip> getDailyTrips() {
        return dailyTrips;
    }

    public void setDailyTrips(List<DailyTrip> dailyTrips) {
        this.dailyTrips = dailyTrips;
    }

    @Override
    public String toString() {
        return super.toString() + "DailyTripListResponse{" +
                "dailyTrips=" + dailyTrips +
                '}';
    }
}
