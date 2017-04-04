package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.Boat;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 4/4/2017.
 */

public class BoatResponse extends Response {
    @SerializedName("boat")
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
