package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Diver extends User {
    @SerializedName(ApiConstant.DIVER_ID)
    private String diverUid;
    @SerializedName(ApiConstant.NAME)
    private String name;

    public Diver() {
    }

    public Diver(String userId, String email) {
        super(userId, email, AccountType.Diver);
    }

    public String getDiverUid() {
        return diverUid;
    }

    public void setDiverUid(String diverUid) {
        this.diverUid = diverUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Diver{" +
                "diverUid=" + diverUid +
                ", name='" + name + '\'' +
                '}';
    }
}
