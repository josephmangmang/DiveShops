package com.divetym.dive.models.response;

import com.divetym.dive.models.Guide;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 5/18/2017.
 */

public class GuideResponse extends Response {

    @SerializedName(ApiConstant.GUIDE)
    private Guide guide;

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }
}
