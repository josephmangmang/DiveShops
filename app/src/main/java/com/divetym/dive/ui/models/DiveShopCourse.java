package com.divetym.dive.ui.models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by kali_root on 3/28/2017.
 */

public class DiveShopCourse {
    @SerializedName("dive_shop_course_id")
    private int diveShopCourseId;
    @SerializedName("dive_shop_id")
    private String diveShopUid;
    @SerializedName("course_id")
    private int courseId;
    @SerializedName("price")
    private BigDecimal price;

    public DiveShopCourse() {
    }

    public DiveShopCourse(int diveShopCourseId, String diveShopUid, int courseId, BigDecimal price) {
        this.diveShopCourseId = diveShopCourseId;
        this.diveShopUid = diveShopUid;
        this.courseId = courseId;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
                ", courseId=" + courseId +
                ", price=" + price +
                '}';
    }
}
