package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.DailyTrip;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DailyTripListResponse extends Response {
    @SerializedName("dive_trips")
    private List<DailyTrip> dailyTrips;

    public List<DailyTrip> getDailyTrips() {
        return dailyTrips;
    }

    public void setDailyTrips(List<DailyTrip> dailyTrips) {
        this.dailyTrips = dailyTrips;
    }
}
