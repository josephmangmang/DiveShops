package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.DailyTrip;
import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DailyTripResponse extends Response {
    @SerializedName(ApiConstant.DAILY_TRIP)
    private DailyTrip dailyTrip;

    public DailyTrip getDailyTrip() {
        return dailyTrip;
    }

    public void setDailyTrip(DailyTrip dailyTrip) {
        this.dailyTrip = dailyTrip;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDailyTripResponse{" +
                "dailyTrip=" + dailyTrip +
                '}';
    }
}
