package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.DiveShop;
import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DiveShopResponse extends Response {
    @SerializedName(ApiConstant.DIVE_SHOP)
    private DiveShop diveShop;

    public DiveShop getDiveShop() {
        return diveShop;
    }

    public void setDiveShop(DiveShop diveShop) {
        this.diveShop = diveShop;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDiveShopResponse{" +
                "diveShop=" + diveShop +
                '}';
    }
}
