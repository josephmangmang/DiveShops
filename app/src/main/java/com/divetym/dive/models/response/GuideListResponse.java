package com.divetym.dive.models.response;

import com.divetym.dive.models.Guide;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 5/16/2017.
 */

public class GuideListResponse extends Response {
    @SerializedName(ApiConstant.GUIDES)
    private List<Guide> guides;

    public List<Guide> getGuides() {
        return guides;
    }

    public void setGuides(List<Guide> guides) {
        this.guides = guides;
    }
}
