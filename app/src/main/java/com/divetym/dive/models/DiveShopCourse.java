package com.divetym.dive.models;

import com.divetym.dive.rest.constants.ApiConstant;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DiveShopCourse extends Course{
    @SerializedName(ApiConstant.DIVE_SHOP_COURSE_ID)
    private int diveShopCourseId;
    @SerializedName(ApiConstant.DIVE_SHOP_ID)
    private String diveShopUid;
    @SerializedName(ApiConstant.PRICE)
    private BigDecimal price;

    public DiveShopCourse() {
    }

    public DiveShopCourse(int diveShopCourseId, String diveShopUid,BigDecimal price) {
        this.diveShopCourseId = diveShopCourseId;
        this.diveShopUid = diveShopUid;
        this.price = price;
    }

    public int getDiveShopCourseId() {
        return diveShopCourseId;
    }

    public void setDiveShopCourseId(int diveShopCourseId) {
        this.diveShopCourseId = diveShopCourseId;
    }

    public String getDiveShopUid() {
        return diveShopUid;
    }

    public void setDiveShopUid(String diveShopUid) {
        this.diveShopUid = diveShopUid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DiveShopCourse{" +
                "diveShopCourseId=" + diveShopCourseId +
                ", diveShopUid='" + diveShopUid + '\'' +
                ", price=" + price +
                '}';
    }
}
