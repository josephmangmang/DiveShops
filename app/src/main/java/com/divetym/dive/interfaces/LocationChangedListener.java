package com.divetym.dive.interfaces;

import com.divetym.dive.models.DiveShopAddress;

/**
 * Created by kali_root on 6/6/2017.
 */

public interface LocationChangedListener {
    void onLocationChanged(DiveShopAddress address);
}
