package com.divetym.dive.models.response;

import com.divetym.dive.models.DiveShop;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DiveShopListResponse extends Response {
    @SerializedName(ApiConstant.DIVE_SHOPS)
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
