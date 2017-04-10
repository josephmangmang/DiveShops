package com.divetym.dive.models.response;

import com.divetym.dive.models.DiveSite;
import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DiveSiteListResponse extends Response {
    @SerializedName(ApiConstant.DIVE_SITES)
    private List<DiveSite> divesites;

    public List<DiveSite> getDivesites() {
        return divesites;
    }

    public void setDivesites(List<DiveSite> divesites) {
        this.divesites = divesites;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDiveSiteListResponse{" +
                "divesites=" + divesites +
                '}';
    }
}
