package com.divetym.dive.ui.models;

import com.divetym.dive.ui.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by kali_root on 3/27/2017.
 */

public class DiveShop extends User {
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.NAME)
    private String name;
    @SerializedName(ApiConstant.DESCRIPTION)
    private String description;
    @SerializedName(ApiConstant.CONTACT_NUMBER)
    private String contactNumber;
    @SerializedName(ApiConstant.ADDRESS)
    private String address;
    @SerializedName(ApiConstant.LATITUDE)
    private double latitiude;
    @SerializedName(ApiConstant.LONGITUDE)
    private double longitude;
    @SerializedName(ApiConstant.PRICE_PER_DIVE)
    private BigDecimal pricePerDive;
    @SerializedName(ApiConstant.SPECIAL_SERVICE)
    private String specialService;

    public DiveShop() {
    }

    public DiveShop(String userId, String email) {
        super(userId, email, UserType.DiveShop);
    }

    public DiveShop(String userId, String email, String diveShopUid, String name, String description, String contactNumber, String address, double latitiude, double longitude, BigDecimal pricePerDive, String specialService) {
        super(userId, email, UserType.DiveShop);
        this.diveShopUid = diveShopUid;
        this.name = name;
        this.description = description;
        this.contactNumber = contactNumber;
        this.address = address;
        this.latitiude = latitiude;
        this.longitude = longitude;
        this.pricePerDive = pricePerDive;
        this.specialService = specialService;
    }

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitiude() {
        return latitiude;
    }

    public void setLatitiude(double latitiude) {
        this.latitiude = latitiude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getPricePerDive() {
        return pricePerDive;
    }

    public void setPricePerDive(BigDecimal pricePerDive) {
        this.pricePerDive = pricePerDive;
    }

    public String getSpecialService() {
        return specialService;
    }

    public void setSpecialService(String specialService) {
        this.specialService = specialService;
    }

    @Override
    public String toString() {
        return "DiveShop{" +
                "diveShopUid=" + diveShopUid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", latitiude=" + latitiude +
                ", longitude=" + longitude +
                ", pricePerDive=" + pricePerDive +
                ", specialService='" + specialService + '\'' +
                '}';
    }
}
