package com.divetym.dive.models.response;

import com.divetym.dive.models.Boat;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 4/4/2017.
 */

public class BoatResponse extends Response {
    @SerializedName(ApiConstant.BOAT)
    private Boat boat;

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @Override
    public String toString() {
        return super.toString() + "\nBoatResponse{" +
                "boat=" + boat +
                '}';
    }
}
