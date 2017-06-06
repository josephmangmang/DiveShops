package com.divetym.dive.interfaces;

import android.location.Address;

import com.divetym.dive.common.DiveShopAddress;

/**
 * Created by kali_root on 6/6/2017.
 */

public interface LocationChangedListener {
    void onLocationChanged(DiveShopAddress address);
}
