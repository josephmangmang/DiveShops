package com.divetym.dive.event;

import com.divetym.dive.models.DiveShop;

/**
 * Created by kali_root on 6/9/2017.
 */

public  class DiveShopEvent {
    private DiveShop diveShop;

    public DiveShopEvent(DiveShop diveShop) {
        this.diveShop = diveShop;
    }

    public DiveShop getDiveShop() {
        return diveShop;
    }

    public void setDiveShop(DiveShop diveShop) {
        this.diveShop = diveShop;
    }
}
