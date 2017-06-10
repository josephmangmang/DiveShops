package com.divetym.dive.event;

import com.divetym.dive.models.Boat;

/**
 * Created by kali_root on 6/9/2017.
 */

public class BoatEvent {
    private Boat boat;

    public BoatEvent(Boat boat) {
        this.boat = boat;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }
}
