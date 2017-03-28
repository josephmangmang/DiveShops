package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Diver extends User {
    @SerializedName("diver_id")
    private int diverUid;
    @SerializedName("name")
    private String name;

    public Diver() {
    }

    public Diver(int userId, String email) {
        super(userId, email, true);
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
}
