package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.DiveSite;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DiveSiteListResponse extends Response {
    @SerializedName("dive_sites")
    private List<DiveSite> divesites;

    public List<DiveSite> getDivesites() {
        return divesites;
    }

    public void setDivesites(List<DiveSite> divesites) {
        this.divesites = divesites;
    }
}
