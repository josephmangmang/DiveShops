package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Diver extends User {
    @SerializedName(ApiConstant.DIVER_ID)
    private int diverUid;
    @SerializedName(ApiConstant.NAME)
    private String name;

    public Diver() {
    }

    public Diver(int userId, String email) {
        super(userId, email, UserType.Diver);
    }

    public int getDiverUid() {
        return diverUid;
    }

    public void setDiverUid(int diverUid) {
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
