package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DiveSite {
    @SerializedName(ApiConstant.DIVE_SITE_ID)
    private int diveSiteId;
    @SerializedName(ApiConstant.NAME)
    private String name;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;
    @SerializedName(ApiConstant.LATITUDE)
    private double latitude;
    @SerializedName(ApiConstant.LONGITUDE)
    private double longitude;

    public DiveSite() {
    }

    public DiveSite(int diveSiteId, String name, String description, double latitude, double longitude) {
        this.diveSiteId = diveSiteId;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getDiveSiteId() {
        return diveSiteId;
    }

    public void setDiveSiteId(int diveSiteId) {
        this.diveSiteId = diveSiteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "DiveSite{" +
                "diveSiteId=" + diveSiteId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

