package com.divetym.dive.event;

import android.location.Address;

import com.divetym.dive.models.LocationAddress;

/**
 * Created by kali_root on 6/11/2017.
 */

public class LocationEvent {
    private LocationAddress locationAddress;

    public LocationEvent(LocationAddress locationAddress) {
        this.locationAddress = locationAddress;
    }

    public LocationAddress getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(LocationAddress locationAddress) {
        this.locationAddress = locationAddress;
    }
}
