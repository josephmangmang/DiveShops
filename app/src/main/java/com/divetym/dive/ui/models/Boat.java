package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class Boat {
    @SerializedName("boat_id")
    private int boatId;
    @SerializedName("dive_shop_id")
    private int diveShopUid;
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String imageUrl;

    public Boat() {
    }

    public Boat(int boatId, int diveShopUid, String name, String imageUrl) {
        this.boatId = boatId;
        this.diveShopUid = diveShopUid;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getBoatId() {
        return boatId;
    }

    public void setBoatId(int boatId) {
        this.boatId = boatId;
    }

    public int getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(int diveShopUid) {
        this.diveShopUid = diveShopUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "boatId=" + boatId +
                ", diveShopUid=" + diveShopUid +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
