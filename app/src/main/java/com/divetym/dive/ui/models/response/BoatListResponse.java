package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.Boat;
import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class BoatListResponse extends Response {
    @SerializedName(ApiConstant.BOATS)
    private List<Boat> boats;

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    @Override
    public String toString() {
        return super.toString() + "\nBoatListResponse{" +
                "boats=" + boats +
                '}';
    }
}
