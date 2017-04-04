package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.DiveShop;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DiveShopListResponse extends Response {
    @SerializedName("dive_shops")
    private List<DiveShop> diveShops;

    public List<DiveShop> getDiveShops() {
        return diveShops;
    }

    public void setDiveShops(List<DiveShop> diveShops) {
        this.diveShops = diveShops;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDiveShopListResponse{" +
                "diveShops=" + diveShops +
                '}';
    }
}
