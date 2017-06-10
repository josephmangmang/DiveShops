package com.divetym.dive.event;

import com.divetym.dive.models.Boat;

import java.util.List;

/**
 * Created by kali_root on 6/9/2017.
 */

public class BoatListEvent {
    private List<Boat> boats;

    public BoatListEvent(List<Boat> boats) {
        this.boats = boats;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}
