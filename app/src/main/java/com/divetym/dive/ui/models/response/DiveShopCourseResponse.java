package com.divetym.dive.ui.models.response;

import com.divetym.dive.ui.models.DiveShopCourse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kali_root on 4/4/2017.
 */

public class DiveShopCourseResponse extends Response {
    @SerializedName("course")
    private DiveShopCourse diveShopCourse;

    public DiveShopCourse getDiveShopCourse() {
        return diveShopCourse;
    }

    public void setDiveShopCourse(DiveShopCourse diveShopCourse) {
        this.diveShopCourse = diveShopCourse;
    }
}
