package com.divetym.dive.event;

import com.divetym.dive.models.DiveShopCourse;

/**
 * Created by kali_root on 6/9/2017.
 */

public class DiveShopCourseEvent {
    private DiveShopCourse diveShopCourse;

    public DiveShopCourseEvent(DiveShopCourse diveShopCourse) {
        this.diveShopCourse = diveShopCourse;
    }

    public DiveShopCourse getDiveShopCourse() {
        return diveShopCourse;
    }

    public void setDiveShopCourse(DiveShopCourse diveShopCourse) {
        this.diveShopCourse = diveShopCourse;
    }
}
