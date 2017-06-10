package com.divetym.dive.event;

import com.divetym.dive.models.DailyTrip;

/**
 * Created by kali_root on 6/10/2017.
 */

public class DailyTripEvent {
    private DailyTrip dailyTrip;

    public DailyTripEvent(DailyTrip dailyTrip) {
        this.dailyTrip = dailyTrip;
    }

    public DailyTrip getDailyTrip() {
        return dailyTrip;
    }

    public void setDailyTrip(DailyTrip dailyTrip) {
        this.dailyTrip = dailyTrip;
    }
}
